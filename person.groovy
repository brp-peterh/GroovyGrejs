import brp.domain.*
import brp.database.*
import brp.tools.*
import brp.gui.MainFrame
import brp.gui.inputfield.TextField
import brp.gui.common.GlobalHotkeyManager

import java.awt.Component
import java.awt.Toolkit
import java.awt.datatransfer.*
import java.awt.event.*
import javax.swing.*


def action = new AbstractAction() {
    @Override
    public void actionPerformed(ActionEvent e) {
        generatePersonID();
    }
}
// Returns a new Swedish personID
private String generatePersonID(){
    def generated = "";
    int max = 99;
    (1..3).each{
        Random rnd = new Random();
        result = rnd.nextInt(max)+ 1;
        if(result < 10)
            generated += "0${result}"
        else
            generated += result.toString();
        // max 12 months
        // max 24 days.
        max = 12 * it;
    }

    Random rnd = new Random();
    generated =  generated + (rnd.nextInt(899) + 100);
    def lastDidgit = Utils.modulo10(generated)


    // Skickar till clipboard
    def pnr = generated + lastDidgit
    pnr = pnr.substring(0,5) + "-" + pnr.substring(6, pnr.length())
    StringSelection ss = new StringSelection(pnr)
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null)

    def currentComponent = MainFrame.getActiveWindow().getFocusOwner()
    if (currentComponent instanceof JTextField) {
        ((JTextField)currentComponent).setText(pnr)
    }

    return pnr;
}

GlobalHotkeyManager.getInstance().addHotKey("PASTEID", KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.ALT_MASK | KeyEvent.CTRL_MASK), action)
