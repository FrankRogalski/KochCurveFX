package tuncer.schule;

import javafx.scene.canvas.Canvas;
import tuncer.util.TurtleFX;

public final class Turtle extends TurtleFX {
	
	public Turtle(final Canvas canvas) {
		super(canvas);
	}
	
	public final void kochCurve(final int depth, final double size) {
		if (depth == 0) {
			this.forward(size);
		}else{
			this.kochCurve(depth-1, size/3);
			this.left(60);
			this.kochCurve(depth-1, size/3);
			this.right(120);
			this.kochCurve(depth-1, size/3);
			this.left(60);
			this.kochCurve(depth-1, size/3);
		}
	}
	
	public final void kochSnowFlake(final int depth, final double size) {
		for(byte i = 0;i < 3;i++) {
			this.kochCurve(depth, size);
			this.right(120);
		}
	}
	
	public final void quadraticTypeOneCurve(final int depth, final double size) {
		if (depth == 0) {
			this.forward(size);
		}else{
			this.quadraticTypeOneCurve(depth-1, size/3);
			this.left(90);
			this.quadraticTypeOneCurve(depth-1, size/3);
			this.right(90);
			this.quadraticTypeOneCurve(depth-1, size/3);
			this.right(90);
			this.quadraticTypeOneCurve(depth-1, size/3);
			this.left(90);
			this.quadraticTypeOneCurve(depth-1, size/3);
		}
	}
	
	public final void quadraticTypeTwoCurve(final int depth, final double size) {
		if (depth == 0) {
			this.forward(size);
		}else{
			this.quadraticTypeTwoCurve(depth-1, size/3);
			this.left(90);
			this.quadraticTypeTwoCurve(depth-1, size/3);
			this.right(90);
			this.quadraticTypeTwoCurve(depth-1, size/3);
			this.right(90);
			this.quadraticTypeTwoCurve(depth-1, size/3);
			this.quadraticTypeTwoCurve(depth-1, size/3);
			this.left(90);
			this.quadraticTypeTwoCurve(depth-1, size/3);
			this.left(90);
			this.quadraticTypeTwoCurve(depth-1, size/3);
			this.right(90);
			this.quadraticTypeTwoCurve(depth-1, size/3);
		}
	}
}