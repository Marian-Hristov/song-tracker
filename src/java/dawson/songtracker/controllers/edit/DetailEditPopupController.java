package dawson.songtracker.controllers.edit;

import dawson.songtracker.controllers.detail.DetailPopupController;
import dawson.songtracker.event.UpdateEntityEvent;
import dawson.songtracker.utils.ICrud;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public abstract class DetailEditPopupController<T> extends DetailPopupController<T> {
    @FXML
    VBox leftCol;

    @FXML
    VBox rightCol;

    private int numberOfFields = 0;

    private Map<TextField, Method> map = new HashMap();

    @Override
    public void hide() {
        super.hide();

        if (leftCol != null) {
            this.leftCol.getChildren().clear();
            this.rightCol.getChildren().clear();
        }
    }

    private ArrayList<Method> getMethods() {
        List<Field> fields = Arrays.stream(entity.getClass().getDeclaredFields())
                .collect(Collectors.toList());

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

        return setters;
    }

    public void createFields() {
        var methods = this.getMethods();

        methods.forEach(method -> {
            if (numberOfFields % 2 == 0) {
                leftCol.getChildren().add(generateHBox(method));
            } else {
                rightCol.getChildren().add(generateHBox(method));
            }

            numberOfFields++;
        });

    }

    private HBox generateHBox(Method method) {
        String name = method.getName();
        Label label = new Label();
        label.setText(name.substring(3));

        if (method.getParameterTypes()[0].equals(List.class)) {
            return arrayListHbox(method, label);
        }

        HBox hbox = new HBox();
        TextField textField = new TextField();
        hbox.getChildren().addAll(label, textField);

        map.put(textField, method);

        return hbox;
    }

    abstract HBox arrayListHbox(Method method, Label label);

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

        rightCol.getChildren().forEach(node -> node.fireEvent(new UpdateEntityEvent()));
        leftCol.getChildren().forEach(node -> node.fireEvent(new UpdateEntityEvent()));


        if (this.getParent() instanceof ICrud) {
            ((ICrud<T>) this.getParent()).updateEntry(this.entity);
            System.out.println("updated entry.");
        }
    }

}
