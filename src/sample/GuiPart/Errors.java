package sample.GuiPart;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import sample.UsersData.Post;



public class Errors {


private static TextFlow area2;


  static StringProperty stringProperty = new SimpleStringProperty("");
  final ChangeListener changeListener = new ChangeListener() {
      @Override
      public void changed(ObservableValue observableValue, Object o, Object t1) {
          Text text = new Text(t1 + "\n");
          text.setFill(Color.RED);
          area2.getChildren().add(text);

      }

  };
 public Errors(TextFlow er){
    stringProperty.addListener(changeListener);
    this.area2 = er;
 }

    static ObservableList<String> str = FXCollections.observableArrayList();

    public synchronized static void AddError(String a) {
        stringProperty.setValue(a);
    }

    public synchronized static void AddInfo(String a) {
  /*   Text text = new Text(a + "\n");
     text.setFill(Color.BLUE);
     area2.getChildren().add(text);
    }
*/
    }

}