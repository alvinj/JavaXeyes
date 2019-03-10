package com.valleyprogramming.javaxeyes;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class FillWindowAction extends MyAbstractAction
{
  public FillWindowAction(JEyesFrame jeyesFrame, String name, KeyStroke keystroke)
  {
    super(jeyesFrame, name, null);
    setupMnemonicAndAccelerator(keystroke);
  }

  public void actionPerformed(ActionEvent e)
  {
    jeyesFrame.handleMakeFullScreenKeystroke();
  }
}

