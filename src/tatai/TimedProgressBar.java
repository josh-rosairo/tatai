package tatai;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

class TimedProgressBar extends ProgressBar {

    private int _counter = 0;
    // Number of updates.
    private int _cycles = 30;
    // Number of milliseconds between updates.
    private int _delay = 10;
    
    /**
     * Starts the progress bar.
     */
    public void start() {
    	_counter = 0;
    	this.setVisible(true);
    	this.setManaged(true);
    	tick();
    }
    
    private void change(double progress) {
    	this.setProgress(progress);
    }
    
    /**
     * Represents one tick of a timer.
     */
    private void tick() {
    	Task<Void> task = new Task<Void>() {
			@Override public Void call(){				
				try {
					if (_counter < _cycles) {
						_counter++;
						change(((double)_counter)/((double)_cycles));
						Thread.sleep(_delay);
						tick();
					} else {
						stop();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
		    }
		};
		new Thread(task).start();
    }
    
    /**
     * Stops and hides the progress bar.
     */
    private void stop() {
    	this.setVisible(false);
    	this.setManaged(false);
    }
    
    /**
     * Initializes the progress bar.
     * @param progress Initial progress.
     */
    public TimedProgressBar(double progress) {
    	super(progress);
    	this.setPrefWidth(300);
    }
    
}