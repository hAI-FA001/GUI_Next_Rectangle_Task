import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainApp extends Application {
    static int width = 750;
    static int height = 500;
    static final int MAX_NUM_SHAPES = 20;
    static LinkedHashMap<Integer, Shape> shapesMap = new LinkedHashMap<>();
    static ArrayList<Shape> rectangles = new ArrayList<>();
    static ArrayList<Shape> circles = new ArrayList<>();
    static TextField indexField = new TextField();
    static Button displayNextRectBtn = new Button("Next");
    static Group container = new Group();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(createScene());
        stage.setTitle("Display Next Rectangle");
        stage.show();
    }

    private static Scene createScene() {
        initRectangleList();
        initComponents();
        
        container.getChildren().addAll(indexField, displayNextRectBtn);

        return new Scene(container, width, height);
    }

    private static void initRectangleList() {
        int numShapesInARow = MAX_NUM_SHAPES/4;

        for (int i = 0; i < MAX_NUM_SHAPES; i++) {
            int genRand = new java.security.SecureRandom().nextInt(255);

            Rectangle rect = new Rectangle(100, 100,
                    Color.rgb((genRand+(i*12))%255,(genRand+(i*3))%255,(genRand+(i*44))%255));
            rect.setWidth(width/numShapesInARow);
            rect.setHeight(height/numShapesInARow);
            rect.setX((i%5)*rect.getWidth());
            rect.setY((i/5)*rect.getHeight());
//            rectangles.add(rect);
            shapesMap.put(i, rect);

//            Circle circle = new Circle(0.25*Math.sqrt(Math.pow(rect.getWidth(), 2)+Math.pow(rect.getHeight(), 2)),
//                    Color.rgb((genRand+(i*12))%255,(genRand+(i*3))%255,(genRand+(i*44))%255));
//            circle.setCenterX(rect.getX()+1.75*circle.getRadius());
//            circle.setCenterY(rect.getY()+1.125*circle.getRadius());
//            circles.add(circle);
//            shapesMap.put(i, circle);
        }
    }

    private static void initComponents(){
        initNextButton();
        initValueField();
    }

    private static void initValueField() {
        indexField.setLayoutX(0.25*width);
        indexField.setLayoutY(0.88*height - displayNextRectBtn.getHeight());
    }

    private static void initNextButton() {
        displayNextRectBtn.setBackground(Background.fill(Color.WHEAT));
        displayNextRectBtn.setTextFill(Color.INDIANRED);
        displayNextRectBtn.setLayoutX(width/2 - displayNextRectBtn.getWidth());
        displayNextRectBtn.setLayoutY(0.88*height - displayNextRectBtn.getHeight());

        displayNextRectBtn.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.N)) {
                ArrayList<Shape> shapeToShow = container.getChildren().size() % 2 == 0? circles : rectangles;
                displayNext(shapeToShow);
            }
        });
        displayNextRectBtn.setOnMouseClicked(mouseEvent -> {
            ArrayList<Shape> argToPass = container.getChildren().size() % 2 == 0? circles : rectangles;
            displayNext(argToPass);
        });
    }

    private static Shape getShapeFromIndexField(){
        return shapesMap.get(Integer.parseInt(indexField.getText()));
    }

    private static <T extends Shape> void displayNext(ArrayList<T> someShape){
        if(container.getChildren().size() <= MAX_NUM_SHAPES) {
//            container.getChildren().add(someShape.get(container.getChildren().size()-1));
            Rectangle shapeToShow;
            try {
                shapeToShow = (Rectangle) getShapeFromIndexField();
            } catch (Exception ignored) {
                return;
            }

            shapeToShow.setX(((container.getChildren().size()-1)%5)*((Rectangle)shapeToShow).getWidth());
            shapeToShow.setY(((container.getChildren().size()-1)/5)*((Rectangle)shapeToShow).getHeight());
            container.getChildren().add(shapeToShow);
        }
    }
}
