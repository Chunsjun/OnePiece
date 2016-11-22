package abc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author shajeer
 */
public class Timer extends javax.swing.JFrame {
    
    private javax.swing.JLabel mTimeLabel;
    private javax.swing.JButton mStartButton;
    private javax.swing.JCheckBox mStatusCheckbox;
    private javax.swing.JButton mStopButton;
    private boolean mDialogAlreadyShowing =false;
    private boolean mIsTimerStarted = false;
    private int mClockTick = 0;
    private static final Runtime mRuntime = Runtime.getRuntime();
    private static int mTimerIdleInSeconds = 0;
    private final static int DOUBLE_CLICK = 2;
    
    public Timer() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        
        mStartButton = new javax.swing.JButton();
        mStopButton = new javax.swing.JButton();
        mTimeLabel = new javax.swing.JLabel();
        mStatusCheckbox = new javax.swing.JCheckBox();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        
        mStartButton.setText("Start");
        mStartButton.setToolTipText("Start timer");
        mStartButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mStartButtonActionPerformed(evt);
            }
        });
        
        mStopButton.setText("Stop");
        mStopButton.setToolTipText("Stop/ Reset timer");
        mStopButton.setEnabled(false);
        mStopButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mStopButtonActionPerformed(evt);
            }
        });
        
        mTimeLabel.setFont(new java.awt.Font("Ubuntu", 1, 24));
        mTimeLabel.setText("00:00:00");
        mTimeLabel.addMouseListener( new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mTimeLabelActionPerformed(e);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        mStatusCheckbox.setFont(new java.awt.Font("Serif", 0, 11));
        mStatusCheckbox.setText("Pause when system goes idle");
        mStatusCheckbox.setToolTipText("Pause when system goes idle");
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(mStatusCheckbox)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(mTimeLabel)
                                .addGap(52, 52, 52))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(mStartButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(mStopButton)
                                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(mTimeLabel)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(mStartButton)
                                        .addComponent(mStopButton))
                                .addGap(10, 10, 10)
                                .addComponent(mStatusCheckbox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        
        mTimeLabel.getAccessibleContext().setAccessibleName("Timer");
        mStatusCheckbox.getAccessibleContext().setAccessibleName("Checkbox");
        
        pack();
    }// </editor-fold>
    
    
    private void mStartButtonActionPerformed(java.awt.event.ActionEvent evt) {
        startTimer();
        mStopButton.setText("Stop");
    }
    
    private void mStopButtonActionPerformed(java.awt.event.ActionEvent evt) {
        mStartButton.setEnabled(true);
        
        if(myTimer.isRunning()){
            myTimer.stop();
            mStopButton.setText("Reset");
        }else{
            mStopButton.setText("Stop");
            mClockTick = 0;
            mStopButton.setEnabled(false);
            mTimeLabel.setText("00:00:00");
        }
    }
    
    private void mTimeLabelActionPerformed(java.awt.event.MouseEvent evt){
        
        if(evt.getClickCount() == DOUBLE_CLICK){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    showTimeEntryDialog();
                }
            }).start();
        }
    }
    
    private void showTimeEntryDialog() {
        String message= "Enter the time in hh:mm:ss format";
        JTextField field = new JTextField();
        
        if (field.getText().length() == 0) {
            field.setText("HH:MM:SS");
            field.setForeground(new Color(150, 150, 150));
        }
        
        field.addFocusListener(new FocusListener() {
            
            @Override
            public void focusGained(FocusEvent e) {
                field.setText("");
                field.setForeground(new Color(50, 50, 50));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                
                if (field.getText().length() == 0) {
                    field.setText("HH:MM:SS");
                    field.setForeground(new Color(150, 150, 150));
                }
                
            }
        });
        
        field.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                String typed = field.getText().replace(":", "");
                int length = typed.length();
                
                if(length != 0 && length % 2 == 0 && field.getText().length() < 8){
                    field.setText(field.getText()+":");
                }
            }
        });
        String title = "Add time";
        int response = JOptionPane.showConfirmDialog(Timer.this, field, title, JOptionPane.OK_CANCEL_OPTION);
        
        if(response == JOptionPane.OK_OPTION) {
            String timeToBeAdded = field.getText().trim();
            int totaltime = 0;
            
            if(timeToBeAdded != null && !timeToBeAdded.equals("HH:MM:SS")){
                
                try{
                    String[] hhMmSs = timeToBeAdded.split(":");
                    
                    if(hhMmSs.length>=1){
                        int hour = Integer.parseInt(hhMmSs[0]);
                        totaltime = hour * 60 * 60;
                    }
                    if(hhMmSs.length>=2){
                        int minutes = Integer.parseInt(hhMmSs[1]);
                        totaltime += minutes * 60;
                    }
                    if(hhMmSs.length>=3){
                        int secs = Integer.parseInt(hhMmSs[2]);
                        totaltime += secs;
                    }
                    mClockTick += totaltime;
                    
                    if(myTimer!=null && !myTimer.isRunning()){
                        String timeToShow = getTimeToDisplay(mClockTick);
                        mTimeLabel.setText(timeToShow);
                    }
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(Timer.this, "Enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void mStatusCheckboxActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
    
    private void formMouseClicked(java.awt.event.MouseEvent evt) {
        
        if(evt.getClickCount() == DOUBLE_CLICK){
            String message = "info4shajeer@gmail.com";
            String title = "Credits";
            JOptionPane.showMessageDialog(Timer.this, message,title, JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
        * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
        */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Timer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Timer().setVisible(true);
            }
        });
    }
       
    private void startTimer() {
        mIsTimerStarted = true;
        myTimer.start();
        mStartButton.setEnabled(false);
        mStopButton.setEnabled(true);
    }
    
    javax.swing.Timer myTimer = new javax.swing.Timer(1000, new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(mIsTimerStarted){
                
                if(mStatusCheckbox.isSelected()){
                    
                    if(!ScreenSaver.isScreenSaverActive()){
                        letTheTimerRun();
                        
                        if(mTimerIdleInSeconds > 0 && !mDialogAlreadyShowing){
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    mDialogAlreadyShowing = true;
                                    handleIdleTime();
                                }
                            }).start();
                        }
                    }else{
                        mTimerIdleInSeconds ++;
                    }
                }else{
                    letTheTimerRun();
                }
            }
        }
        
        private void letTheTimerRun() {
            mClockTick++;
            String timeToShow = getTimeToDisplay(mClockTick);
            mTimeLabel.setText(timeToShow);
        }
        
        private void handleIdleTime() {
            String timeToShow = getTimeToDisplay(mTimerIdleInSeconds);
            String dialogMessage = "We have detected "+ timeToShow+ " system idle time,\n would you like to add it ?";
            String title = "Idle time detected";
            int dialogResul = JOptionPane.showConfirmDialog(Timer.this, dialogMessage, title, JOptionPane.CANCEL_OPTION);
            
            if(dialogResul == JOptionPane.OK_OPTION){
                mClockTick += mTimerIdleInSeconds;
                mTimerIdleInSeconds = 0;
                mDialogAlreadyShowing =false;
            }else{
                mTimerIdleInSeconds = 0;
                mDialogAlreadyShowing =false;
            }
        }
    });
    
    private String getTimeToDisplay(int clockTick){
        int[] time = splitToComponentTimes(clockTick);
        String hour = String.format("%02d", time[0]);
        String minutes = String.format("%02d", time[1]);
        String seconds = String.format("%02d", time[2]);
        String timeToShow = hour+":"+minutes+":"+seconds;
        return timeToShow;
    }
    
    public static int[] splitToComponentTimes(int seconds)    {
        int hours = (int) seconds / 3600;
        int remainder = (int) seconds - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;
        int[] ints = {hours , mins , secs};
        return ints;
    }
    
    static class ScreenSaver {
        
        private static final String COMMAND = "gnome-screensaver-command -q |  grep -q 'is active'";
        private static final String[] OPEN_SHELL = { "/bin/sh", "-c", COMMAND };
        private static final int EXPECTED_EXIT_CODE = 0;
        
        public static boolean isScreenSaverActive() {
            Process process = null;
            
            try {
                process = mRuntime.exec(OPEN_SHELL);
                return process.waitFor() == EXPECTED_EXIT_CODE;
            } catch(final IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }
        
    }
}
