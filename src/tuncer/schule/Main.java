package tuncer.schule;

import java.text.DecimalFormat;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public final class Main extends Application {
	
	private Turtle t;
	private Canvas can;
	
	private ComboBox<String> cmb_curve;
	private Slider sl_depth;
	private Text txt_step;
	
	private double zoom = 1;
	
	public final static void main(final String[] args) {
		launch(args);
	}

	@Override
	public final void start(final Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		final Scene scene = new Scene(root, 800, 600);
		
		primaryStage.setMinWidth(640);
		primaryStage.setMinHeight(480);  
	    
	    root.setTop(this.buildTop());
	    root.setCenter(this.buildCenter());
		root.setBottom(this.buildBottom());
		
		this.drawCurve(this.cmb_curve.getSelectionModel().getSelectedIndex(), (int)this.sl_depth.getValue());
		
		scene.widthProperty().addListener((obv, oldVal, newVal) -> {
			this.can.setWidth((double)newVal);
			this.drawCurve(this.cmb_curve.getSelectionModel().getSelectedIndex(), (int)this.sl_depth.getValue());
		});
		
		scene.heightProperty().addListener((obv, oldVal, newVal) -> {
			this.can.setHeight((double)newVal-100);
			this.drawCurve(this.cmb_curve.getSelectionModel().getSelectedIndex(), (int)this.sl_depth.getValue());
		});
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(final KeyEvent e) {
				if (e.getCode() == KeyCode.Z) {
					zoom += 0.1;
					drawCurve(cmb_curve.getSelectionModel().getSelectedIndex(), (int)sl_depth.getValue());
				}else
					if (e.getCode() == KeyCode.Y) {
						zoom -= 0.1;
						if (zoom < 0.1) {
							zoom = 0.1;
						}
						drawCurve(cmb_curve.getSelectionModel().getSelectedIndex(), (int)sl_depth.getValue());
					}

			}
		});
		
		primaryStage.setTitle("CurveFX");
		primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.png")));
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private Node buildTop() {
		HBox hbox = new HBox();
		
		hbox.setStyle("-fx-background-color: #CCCCCC;");
		
		hbox.setPadding(new Insets(15, 15, 15, 15));
		
		hbox.getChildren().add((new Text("BBS Rotenburg - Informatik Kurs")));
		
		return hbox;
	}
	
	private Node buildCenter() {
		this.can = new Canvas(800, 500);
		this.t = new Turtle(this.can);
		this.t.showMessage(false);	
		
		return this.can;
	}
	
	private Node buildBottom() {
		HBox hbox = new HBox();
		
	    hbox.setStyle("-fx-background-color: #E5E5E5;");
	    
		hbox.setPadding(new Insets(15, 15, 15, 15));
		hbox.setSpacing(15);
		
		ObservableList<String> obsvl_curve = FXCollections.observableArrayList("Koch-Curve", "Kochsnowflake", "Quadratic type 1 curve", "Quadratic type 2 curve");
		this.cmb_curve = new ComboBox<String>(obsvl_curve);
		this.cmb_curve.getSelectionModel().select(0);
		
		this.cmb_curve.valueProperty().addListener((obv, oldVal, newVal) -> {
			this.zoom = 1;
			this.drawCurve(this.cmb_curve.getSelectionModel().getSelectedIndex(), (int)this.sl_depth.getValue());
		});
		
		this.sl_depth = new Slider(0, 10, 0);
		
		this.sl_depth.setShowTickLabels(true);
		this.sl_depth.setBlockIncrement(1);
		
		
		this.sl_depth.setSnapToTicks(true);
		this.sl_depth.setMinorTickCount(10);
		this.sl_depth.setMajorTickUnit(1);
		
		this.sl_depth.valueProperty().addListener((obv, oldVal, newVal) -> {
			this.sl_depth.setValue(newVal.intValue());
			this.zoom = 1;
			
			this.drawCurve(this.cmb_curve.getSelectionModel().getSelectedIndex(), (int)this.sl_depth.getValue());
		});
		
		this.txt_step = new Text();
		
	    hbox.getChildren().addAll(this.cmb_curve, this.sl_depth, this.txt_step);
	    
	    return hbox;
	}
	
	private void drawCurve(int curve, int depth) {
		this.t.clear();
		this.t.penUp();
		switch (curve) {
			case 0:
				this.t.moveTo(0, this.can.getHeight()-5);
				this.t.penDown();
				
				this.t.kochCurve(depth, this.can.getWidth() * this.zoom);
				break;
			case 1:
				this.t.moveTo(((this.can.getWidth()/2) - (this.can.getWidth()/3) /2), (this.can.getHeight()/2) - 100);
				this.t.penDown();
				
				this.t.kochSnowFlake(depth, (this.can.getWidth()/3) * this.zoom);
				break;
			case 2:
				this.t.moveTo(0, this.can.getHeight()-5);
				this.t.penDown();
				
				this.t.quadraticTypeOneCurve(depth, this.can.getWidth() * this.zoom);
				break;
			case 3:
				this.t.moveTo(0, this.can.getHeight()/2);
				this.t.penDown();
				
				this.t.quadraticTypeTwoCurve(depth, (this.can.getWidth()/10) * this.zoom);
		}
		this.txt_step.setText(new DecimalFormat("###,##0").format(this.t.getStep()) + " " + handleSteps(this.t.getStep()));
		this.t.format();
		this.t.showMessage(false);
	}

	private final String handleSteps(final int step) {
		return (step==1) ? "Step":"Steps";
	}
}