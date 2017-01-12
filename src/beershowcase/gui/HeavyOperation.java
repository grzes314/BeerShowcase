
package beershowcase.gui;

import java.beans.PropertyChangeEvent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

/**
 *
 * @author Grzegorz Łoś
 */
public abstract class HeavyOperation {
    
    private final JDialog dialog;

    public HeavyOperation(String desc) {
        dialog = new JDialog(RunningApplication.MainFrame, true);
        dialog.setTitle("Please wait");
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(RunningApplication.MainFrame);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        JLabel label = new JLabel(desc);
        dialog.setContentPane(label);
    }
    
    
    protected abstract void timeConsumingTask();
    
    public void execute() {
        SwingWorker<Void, Void> sWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                timeConsumingTask();
                return null;
            }
            
            @Override
            protected void done() {
                firePropertyChange("done", null, null);
            }
        };
        sWorker.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            dialog.dispose();
        });
        sWorker.execute();
        dialog.setVisible(true);
    }
}
