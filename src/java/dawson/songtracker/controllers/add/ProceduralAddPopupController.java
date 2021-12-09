package dawson.songtracker.controllers.add;

import dawson.songtracker.Cache;
import dawson.songtracker.CacheManager;
import dawson.songtracker.customNodes.ComboBoxItemWrap;
import dawson.songtracker.event.UpdateEntityEvent;
import dawson.songtracker.types.Builder;
import dawson.songtracker.types.DatabaseObject;
import dawson.songtracker.utils.ComboBoxCheckBuilder;
import dawson.songtracker.utils.ICrud;
import dawson.songtracker.utils.Loader;
import dawson.songtracker.utils.Popup;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ProceduralAddPopupController<T extends DatabaseObject, K extends Builder<T>> extends Popup {
    private final Class<T> entityClass;
    private K builder;
    private int numberOfFields = 0;
    private Map<TextField, Method> map = new HashMap<>();

    @FXML
    VBox leftCol;

    @FXML
    VBox rightCol;

    public ProceduralAddPopupController(Class<T> entityClass, K builder) {
        this.entityClass = entityClass;
        this.builder = builder;

        try {
            var fxmlLoader = Loader.Load("detail/DefaultEditDetailController");
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        super.show();
        createFields();
    }

    @Override
    public void hide() {
        super.hide();
        numberOfFields = 0;

        if (leftCol != null) {
            this.leftCol.getChildren().clear();
            this.rightCol.getChildren().clear();
        }
    }

    public void createFields() {
        var methods = this.getMethods();

        System.out.println("here.");
        methods.forEach(System.out::println);

        methods.forEach(setter -> {
            if (numberOfFields % 2 == 0) {
                leftCol.getChildren().add(generateHBox(setter));
            } else {
                rightCol.getChildren().add(generateHBox(setter));
            }

            numberOfFields++;
        });

    }
    /**
     * Gets all the fields with "get", "set" or "setFinal".
     * @return
     */
    protected ArrayList<Method> getMethods() {
        List<Field> fields = Arrays.stream(builder.getClass().getDeclaredFields())
                .collect(Collectors.toList());


//        var parentFields = Arrays.stream(entityClass.getClass().getSuperclass().getDeclaredFields()).collect(Collectors.toList());
//        fields.addAll(parentFields);

        Method[] methods = new Method[0];
        try {
            methods = Class.forName(builder.getClass().getName()).getMethods();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Method> setters = new ArrayList<>();

        // Gets every single setter on a class.
        for (int i = 0; i < methods.length; i++) {
            String name = methods[i].getName().toLowerCase();
            System.out.println(name);
            if (name.startsWith("set")) {
                for (int j = 0; j < fields.size(); j++) {
                    if (name.contains(fields.get(j).getName())) {
                        setters.add(methods[i]);
                    }
                }
            }
        }

        return setters;
    }

    /**
     * Assuming we have a builder:
     * EntityBuilder(String f1, ArrayList<T> field2)
     * And we have setters for the builder.
     * EntityBuilder.
     */
    private HBox generateHBox(Method setter) {
        String name = setter.getName();
        Label label = new Label();
        label.setText(name.substring(3));

        // The argument type.
        var type = setter.getParameterTypes()[0];
        // If the type is an object class.
        if (DatabaseObject.class.isAssignableFrom(type)) {
            return arrayListHbox(setter, label, type);
        } else if (List.class.isAssignableFrom(type)) {
            // That's when we pray the type is ArrayList<DatabaseObject>
            return multipleArrayListHBox(setter, label, type);
        }

        HBox hbox = new HBox();
        TextField textField = new TextField();
        hbox.getChildren().addAll(label, textField);

        map.put(textField, setter);

        return hbox;
    }

    protected HBox multipleArrayListHBox(Method setter, Label label, Class type) {

        var pType = (ParameterizedType) setter.getGenericParameterTypes()[0];
        try {
            type = Class.forName(pType.getActualTypeArguments()[0].getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Cache<DatabaseObject> cache = CacheManager.getCache(type);
        List uncheckedArray = (List) cache
                .getCachedItems();

        if (!cache.isLoaded()) {
            System.out.println("Cache is not loaded.");
        }

        ComboBox<ComboBoxItemWrap> cb = ComboBoxCheckBuilder.ComboBox(new ArrayList<>(), uncheckedArray);
        HBox hBox = new HBox();
        hBox.getChildren().add(label);
        hBox.getChildren().add(cb);


        hBox.addEventHandler(UpdateEntityEvent.UPDATE_ENTITY, k -> {
            var checkedItems = cb.getItems()
                    .filtered(f-> f!=null)
                    .filtered(f-> f.getCheck())
                    .stream().map(f -> f.getItem())
                    .collect(Collectors.toList());

            try {
                this.builder = (K) setter.invoke(builder, checkedItems);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        });

        return hBox;

    }

    protected HBox arrayListHbox(Method method, Label label, Class dataType) {
        HBox hbox = new HBox();
        hbox.getChildren().add(label);

        Cache cache = CacheManager.getCache(dataType);
        var options = cache.getCachedItems();

        Object selected = null;

        var comboBox = ComboBoxCheckBuilder.singleComboBox(options, selected);
        hbox.getChildren().add(comboBox);

        hbox.addEventHandler(UpdateEntityEvent.UPDATE_ENTITY, event -> {
            try {
                this.builder = (K) method.invoke(builder, comboBox.getValue());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        return hbox;
    }

    @FXML
    public void onUpdate() {
        map.forEach((textField, setter) -> {
            try {
                setter.invoke(builder, textField.getText());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        rightCol.getChildren().forEach(node -> node.fireEvent(new UpdateEntityEvent()));
        leftCol.getChildren().forEach(node -> node.fireEvent(new UpdateEntityEvent()));

        var entity = builder.build();

        if (this.getParent() instanceof ICrud) {
            ((ICrud<T>) this.getParent()).addNewEntry(entity);
        }
    }

}
