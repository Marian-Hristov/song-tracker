package dawson.songtracker.front.controllers.edit;

import dawson.songtracker.front.Cache;
import dawson.songtracker.front.CacheManager;
import dawson.songtracker.front.customNodes.ComboBoxItemWrap;
import dawson.songtracker.front.event.UpdateEntityEvent;
import dawson.songtracker.back.types.DatabaseObject;
import dawson.songtracker.front.utils.ComboBoxCheckBuilder;
import dawson.songtracker.front.utils.Loader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class DefaultDetailEditController<T extends DatabaseObject> extends DetailEditPopupController<T> {
    @Override
    public void show(T entity) {
        this.entity = entity;
        this.createFields();
        this.show();
    }

    public DefaultDetailEditController() {
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
    protected HBox multipleArrayListHBox(Method setter, Label label, Class type, Method getter) {

        var list = getter.getReturnType();
        var pType = (ParameterizedType) getter.getGenericReturnType();
        try {
            type = Class.forName(pType.getActualTypeArguments()[0].getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Cache<DatabaseObject> cache = CacheManager.getCache(type);

        ArrayList getResult = null;
        try {
            // Cry and pray
            getResult = (ArrayList) getter.invoke(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        List uncheckedArray = (List) cache
                .getCachedItems()
                .stream()
                .filter(Predicate.not(getResult::contains))
                .collect(Collectors.toList());

        if (!cache.isLoaded()) {
            System.out.println("Cache is not loaded.");
        }

        var checkedArray = getResult;

        ComboBox<ComboBoxItemWrap> cb = ComboBoxCheckBuilder.ComboBox(checkedArray, uncheckedArray);
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
                setter.invoke(entity, checkedItems);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        return hBox;

    }

    @Override
    protected Map<Method, Method> getMethods() {
        return super.getMethods();
    }
}
