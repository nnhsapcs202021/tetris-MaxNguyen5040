import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JBrainTetris extends JTetris {
    private Brain controller;
    private boolean enabled;
    private JButton enabler;
    private Move goalMove;

    JBrainTetris(int width, int height) {
        super(width, height);
    }

    @Override
    public Container createControlPanel() {
        Container panel = super.createControlPanel();
        ArrayList<Brain> brains = BrainFactory.createBrains();
        Brain[] brains1 = new Brain[2];
        brains1[0] = brains.get(0);
        brains1[1] = brains.get(1);

        JComboBox box = new JComboBox(brains1);
        this.controller = brains.get(0);

        ListeningClass listener = new ListeningClass();
        box.addActionListener(listener);
        panel.add(box);

        this.enabled = false;
        this.enabler = new JButton("Enable Brain");
        ListeningClass2 listener2 = new ListeningClass2();
        enabler.addActionListener(listener2);
        panel.add(enabler);

        return panel;
    }


    public class ListeningClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox) e.getSource();
            Brain chosen = (Brain) cb.getSelectedItem();
            controller = chosen;
        }
    }

    public class ListeningClass2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (enabled == true) {
                enabled = false;
                enabler.setText("Enable Brain");
            } else {
                enabled = true;
                enabler.setText("Disable Brain");
            }

        }

    }


    @Override
    public Piece pickNextPiece() {
        Piece piece = super.pickNextPiece();
        this.currentPiece = piece;
        if (enabled == true){
            this.goalMove = controller.bestMove(board, piece, HEIGHT + TOP_SPACE);
        }
        return piece;

    }

    @Override
    public void tick(int verb) {
        if (enabled == true && verb == DOWN) {
            if (this.currentPiece != this.goalMove.getPiece()){ //not correct orientation
                super.tick(ROTATE);
            }

            if (this.currentX > this.goalMove.getX()) {
                super.tick(LEFT);
            }

            else if (this.currentX < this.goalMove.getX()) {
                super.tick(RIGHT);
            }

            }
        super.tick(verb);
    }




}

