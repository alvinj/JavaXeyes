package com.valleyprogramming.javaxeyes;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;

public abstract class MyAbstractAction extends AbstractAction
{
  protected JEyesFrame jeyesFrame;
  
  public MyAbstractAction(JEyesFrame jeyesFrame, String name, Icon icon)
  {
    super(name, icon);
    this.jeyesFrame = jeyesFrame;
  }
  
  void setupMnemonicAndAccelerator(KeyStroke keystroke)
  {
    putValue(MNEMONIC_KEY, keystroke.getKeyCode());
    putValue(ACCELERATOR_KEY, keystroke);
  }

}
