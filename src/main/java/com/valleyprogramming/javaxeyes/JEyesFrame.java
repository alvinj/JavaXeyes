package com.valleyprogramming.javaxeyes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;
import java.awt.event.InputEvent;

public class JEyesFrame extends JFrame
{
  private final JEyesFrame thisFrame;

  // our actions
  private Action fillWindowAction;
  private Action chooseColorAction;
  
  private KeyStroke fillWindowKeystroke  = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.META_DOWN_MASK);
  private KeyStroke chooseColorKeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.META_DOWN_MASK);
  
  private JavaXeyes mainController;
  private JComponent jEyesPanel;

  // make it blue for debugging, but transparent for actual Production use:
  private Color faceBackgroundColor = new Color(0, 0, 255, 0);

  /**
   * @param mainController
   * @param jEyesPanel Our "xeyes" panel. It has a DragWindowListener attached to it.
   */
  public JEyesFrame(JavaXeyes mainController, JComponent jEyesPanel)
  {
      // bookkeeping
      this.mainController = mainController;
      this.thisFrame = this;
      this.jEyesPanel = jEyesPanel;

      // configure the jframe
      this.getContentPane().add(jEyesPanel);
      this.setResizable(false);
      this.setUndecorated(true);
      this.setBackground(faceBackgroundColor);

      // make it yellow for debugging, but transparent for actual Production use:
      thisFrame.getContentPane().setBackground(new Color(255, 255, 0, 0));
      thisFrame.setOpacity(0.5f);

      // menubar
      addActions();
      this.setJMenuBar(createMenuBar());
  }

  private void addActions()
  {
      fillWindowAction = new FillWindowAction(this, "Fill Window", fillWindowKeystroke);
      jEyesPanel.getInputMap().put(fillWindowKeystroke, "fillWindow");
      jEyesPanel.getActionMap().put("fillWindow", fillWindowAction);
  }
  
  private JMenuBar createMenuBar()
  {
      // create the menubar
      JMenuBar menuBar = new JMenuBar();

      // create menu
      JMenu actionsMenu = new JMenu("Actions");
      
      // create menu items
      JMenuItem fillWindowMenuItem = new JMenuItem(fillWindowAction);

      // add items to menu
      actionsMenu.add(fillWindowMenuItem);

      // add the menus to the menubar
      menuBar.add(actionsMenu);

      return menuBar;
  }
  
  public void display(final int height, final int width)
  {
      SwingUtilities.invokeLater(new Runnable()
      {
          public void run()
          {
              thisFrame.setSize(new Dimension(height, width));

              // center the frame
              thisFrame.setLocationRelativeTo(null);

              // display the frame
              thisFrame.setVisible(true);
              thisFrame.transferFocus();
          }
      });
  }
  
  public void setBackgroundColorOfFace(Color faceBackgroundColor)
  {
      this.faceBackgroundColor = faceBackgroundColor;
      this.setBackground(faceBackgroundColor);
  }

  /**
   * A callback someone else can use to make us full-screen.
   */
  public void handleMakeFullScreenKeystroke()
  {
      mainController.makeFrameFullScreen();
  }
  
}



