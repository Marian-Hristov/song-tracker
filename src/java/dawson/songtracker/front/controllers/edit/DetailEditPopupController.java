package dawson.songtracker.front.controllers.edit;

import dawson.songtracker.front.Cache;
import dawson.songtracker.front.CacheManager;
import dawson.songtracker.front.controllers.detail.DetailPopupController;
import dawson.songtracker.front.event.UpdateEntityEvent;
import dawson.songtracker.back.types.DatabaseObject;
import dawson.songtracker.front.utils.ComboBoxCheckBuilder;
import dawson.songtracker.front.utils.ICrud;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public abstract class DetailEditPopupController<T extends DatabaseObject> extends DetailPopupController<T> {
    @FXML
    VBox leftCol;

    @FXML
    VBox rightCol;

    private int numberOfFields = 0;

    private Map<TextField, Method> map = new HashMap();

    @Override
    public void hide() {
        super.hide();
        numberOfFields = 0;

        if (leftCol != null) {
            this.leftCol.getChildren().clear();
            this.rightCol.getChildren().clear();
        }
    }

    protected Map<Method, Method> getMethods() {
        List<Field> fields = Arrays.stream(entity.getClass().getDeclaredFields())
                .collect(Collectors.toList());

        var parentFields = Arrays.stream(entity.getClass().getSuperclass().getDeclaredFields()).collect(Collectors.toList());
        fields.addAll(parentFields);

        var methods = entity.getClass().getMethods();
        ArrayList<Method> setters = new ArrayList<>();

        // Gets every single setter on a class.
        for (int i = 0; i < methods.length; i++) {
            String name = methods[i].getName().toLowerCase();
            if (name.startsWith("set")) {
                for (int j = 0; j < fields.size(); j++) {
                    if (name.contains(fields.get(j).getName())) {
                        setters.add(methods[i]);
                    }
                }
            }
        }

        Map<Method, Method> gettersAndSetters = new HashMap<>();

        setters.forEach(m -> {
            var basicName = m.getName().toLowerCase().substring(3);

            for (int i = 0; i < methods.length; i++) {
                String name = methods[i].getName().toLowerCase();
                if (name.startsWith("get") && name.substring(3).equals(basicName)) {
                    gettersAndSetters.put(methods[i], m);
                }
            }
        });

        return gettersAndSetters;
    }

    public void createFields() {
        var methods = this.getMethods();

        methods.forEach((getter, setter)-> {
            if (numberOfFields % 2 == 0) {
                leftCol.getChildren().add(generateHBox(getter, setter));
            } else {
                rightCol.getChildren().add(generateHBox(getter, setter));
            }

            numberOfFields++;
        });

    }

    protected void addMoreNodes(Node node) {
        if (numberOfFields % 2 == 0) {
            leftCol.getChildren().add(node);
        } else {
            rightCol.getChildren().add(node);
        }
        numberOfFields++;

    }

    private HBox generateHBox(Method getter, Method setter) {
        String name = getter.getName();
        Label label = new Label();
        label.setText(name.substring(3));

        var type = setter.getParameterTypes()[0];
        if (DatabaseObject.class.isAssignableFrom(type)) {
            return arrayListHbox(setter, label, type, getter);
        } else if (List.class.isAssignableFrom(type)) {
            // That's when we pray the type is ArrayList<DatabaseObject>
            return multipleArrayListHBox(setter, label, type, getter);
        }

        HBox hbox = new HBox();
        TextField textField = new TextField();

        Object obj = null;
        try {
            obj = getter.invoke(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        textField.setPromptText(obj.toString());
        hbox.getChildren().addAll(label, textField);

        map.put(textField, setter);

        return hbox;
    }

    protected abstract HBox multipleArrayListHBox(Method setter, Label label, Class type, Method getter);

    HBox arrayListHbox(Method method, Label label, Class dataType, Method getter) {
        HBox hbox = new HBox();
        hbox.getChildren().add(label);

        Cache cache = CacheManager.getCache(dataType);
        var options = cache.getCachedItems();

        Object selected = null;
        try {
            selected = getter.invoke(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        var comboBox = ComboBoxCheckBuilder.singleComboBox(options, selected);
        hbox.getChildren().add(comboBox);

        hbox.addEventHandler(UpdateEntityEvent.UPDATE_ENTITY, event -> {
            try {
                method.invoke(entity, comboBox.getValue());
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
                setter.invoke(this.entity, textField.getText());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        map.clear();

        rightCol.getChildren().forEach(node -> node.fireEvent(new UpdateEntityEvent()));
        leftCol.getChildren().forEach(node -> node.fireEvent(new UpdateEntityEvent()));


        if (this.getParent() instanceof ICrud) {
            try {
                ((ICrud<T>) this.getParent()).updateEntry(this.entity, this.oldEntity);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
