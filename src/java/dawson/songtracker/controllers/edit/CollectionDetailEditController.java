package dawson.songtracker.controllers.edit;

import dawson.songtracker.customNodes.ComboBoxItemWrap;
import dawson.songtracker.dbObjects.objectLoaders.dowloader.ObjectDownloader;
import dawson.songtracker.event.UpdateEntityEvent;
import dawson.songtracker.types.components.Compilation;
import dawson.songtracker.types.distributions.Collection;
import dawson.songtracker.utils.ComboBoxCheckBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionDetailEditController extends DefaultDetailEditController<Collection> {
    @Override
    public HBox arrayListHbox(Method method, Label label) {
        HBox hBox = new HBox();
        hBox.getChildren().add(label);

        try {
            var uncheckedArray= ObjectDownloader.getInstance()
                    .loadAllCompilations()
                    .stream()
                    .filter(Predicate.not(this.entity.getCompilations()::contains))
                    .collect(Collectors.toList());

            var checkedArray = this.entity.getCompilations();

            ComboBox<ComboBoxItemWrap<Compilation>> cb = ComboBoxCheckBuilder.ComboBox(checkedArray, uncheckedArray);
            hBox.getChildren().add(cb);


            hBox.addEventHandler(UpdateEntityEvent.UPDATE_ENTITY, k -> {
                System.out.println("UPDATE_ENTITY EVENT!");
                var checkedItems = cb.getItems()
                        .filtered(f-> f!=null)
                        .filtered(f-> f.getCheck())
                        .stream().map(f -> f.getItem())
                        .collect(Collectors.toList());

                checkedItems.forEach(System.out::println);


                this.entity.setCompilations(checkedItems);
            });

            return hBox;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}
