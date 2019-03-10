package com.valleyprogramming.javaxeyes;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
//import com.apple.eawt.Application;
import com.sun.awt.AWTUtilities;

/**
 * This is a Java version of the old X-Windows xeyes application.
 * Created by Alvin Alexander of http://www.devdaily.com and
 * http://www.valleyprogramming.com
 */
public class JavaXeyes
{

  private static final String APP_NAME = "Java Eyes";
  private static final String APP_URL = "http://www.valleyprogramming.com";
  private static final String ABOUT_DIALOG_MESSAGE = "<html><center><p>Java Eyes<br><font color=\"#939393\">version 0.2</font></p></center>\n\n"
    + "<center><p><font color=\"#939393\">created by ...</font></p></center>\n"
    + "<center><p><a href=\"http://www.valleyprogramming.com\"> www.valleyprogramming.com </a></p><center>\n";
  
  private JEyesFrame jEyesFrame;
  private JEyesPanel jEyesPanel;
  
  // location of our jframe on the screen
  private int frameX = 0;
  private int frameY = 0;

  // normal (small) size properties
  private static final int   SMALL_FRAME_HEIGHT         = 150;
  private static final int   SMALL_FRAME_WIDTH          = 303;
  private static final int   SMALL_CIRCLE_DIAMETER      = 145;
  private static final int   SMALL_CIRCLE_PEN_THICKNESS = 3;
  private static final int   SMALL_LEFT_EYE_X           = 1;
  private static final int   SMALL_LEFT_EYE_Y           = 1;
  private static final int   SMALL_RIGHT_EYE_X          = 150;
  private static final int   SMALL_RIGHT_EYE_Y          = 1;
  private static final int   SMALL_PUPIL_DIAMETER       = 30;
  private static final Color SMALL_PUPIL_PEN_COLOR      = Color.DARK_GRAY;
 
  // full-screen properties
  private static final int   LARGE_CIRCLE_PEN_THICKNESS = 16;
  private static final int   LARGE_PUPIL_DIAMETER       = 100;
  private static final Color LARGE_PUPIL_PEN_COLOR      = Color.BLUE;
  private static final Color LARGE_BACKGROUND_COLOR     = Color.ORANGE;
  
  // values for our frame and panel
  private int frameHeight        = SMALL_FRAME_HEIGHT;
  private int frameWidth         = SMALL_FRAME_WIDTH;
  private int circleDiameter     = SMALL_CIRCLE_DIAMETER;
  private int circlePenThickness = SMALL_CIRCLE_PEN_THICKNESS;
  private Color penColor         = Color.BLACK;

  // values for the left and right eye
  private int leftEyeX           = SMALL_LEFT_EYE_X;
  private int leftEyeY           = SMALL_LEFT_EYE_Y;
  private int rightEyeX          = SMALL_RIGHT_EYE_X;
  private int rightEyeY          = SMALL_RIGHT_EYE_Y;
  private int pupilDiameter      = SMALL_PUPIL_DIAMETER;

  // the mouse cursor position
  private int mouseX;
  private int mouseY;
  private int lastMouseX;
  private int lastMouseY;
  private static final int SLEEP_TIME_MOUSE_MOVING     = 100;
  private static final int SLEEP_TIME_MOUSE_NOT_MOVING = 500;
  private static final int SLEEP_TIME_BEFORE_QUITTING  = 1000;
 
  private Point lastFrameLocation;
  private boolean inSmallDisplayMode = true;
  private boolean inRunMode = true;

  // the 'eyes' glass pane
  private EyesGlassPane eyesGlassPane;

  /**
   * A Java version of the famous Xeyes application.
   */
  public static void main(String[] args)
  {
    new JavaXeyes();
  }
  
  public JavaXeyes()
  {
    dieIfNotRunningOnMacOsX();
    configureForMacOSX();
    
    // construct our panel, then add the drag listener to it
    // TODO finish refactoring this
    jEyesPanel = new JEyesPanel(this, circlePenThickness, penColor);
    jEyesPanel.setEyeSizeAndLocation(leftEyeX, leftEyeY, rightEyeX, rightEyeY);
    jEyesPanel.setEyeDiameter(circleDiameter);

    DragWindowListener dragWindowListener = new DragWindowListener(jEyesPanel);
    jEyesPanel.addMouseListener(dragWindowListener);
    jEyesPanel.addMouseMotionListener(dragWindowListener);
    
    // create and display our jframe
    jEyesFrame = new JEyesFrame(this, jEyesPanel);

    // create our 'eyes' glass pane, and at it to the frame
    eyesGlassPane = new EyesGlassPane(this);
    jEyesFrame.setGlassPane(eyesGlassPane);
    jEyesFrame.getGlassPane().setVisible(true);

    // display our jframe
    jEyesFrame.display(frameWidth, frameHeight);
    frameX = jEyesFrame.getX();
    frameY = jEyesFrame.getX();
    lastFrameLocation = new Point(frameX, frameY);
    
    // these AWTUtilities settings come from Java SE 6u10
    // this replaces the mac-specific property i was setting before (window.alpha)
    AWTUtilities.setWindowOpacity(jEyesFrame, 1.0f);
    // this line does the trick of making the frame see-thru, but keeping my circles,
    // but, it made the eyes hard to grab (until i filled them with white). see this url:
    // http://java.sun.com/developer/technicalArticles/GUI/translucent_shaped_windows/#Enabling-Per-Pixel-Translucency
    AWTUtilities.setWindowOpaque(jEyesFrame, false);

    // this is the main loop:
    // 1) get the mouse cursor position
    // 2) move the eyes to the right spot
    // 3) sleep for a bit
    eyesGlassPane.updateLocation(mouseX, mouseY, pupilDiameter, pupilDiameter);
    Point leftEyeballPoint = null;
    Point rightEyeballPoint = null;
    while (inRunMode)
    {
        // sets mouseX and mouseY
        getCurrentMouseLocation();

        // get the current location of our frame
        frameX = jEyesFrame.getX();
        frameY = jEyesFrame.getY();

        leftEyeballPoint = getPupilCoordinates("left");
        rightEyeballPoint = getPupilCoordinates("right");

        eyesGlassPane.updateEyes(leftEyeballPoint.x, leftEyeballPoint.y, rightEyeballPoint.x, rightEyeballPoint.y);
        eyesGlassPane.repaint();

        // sleep
        sleep(getSleepTime());
        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }
    
    // come here when the user says 'quit' (i.e., when inRunMode = false)
    //doLastAnimationAndExit();
    //TODO in 2019, this animation doesn’t work after i removed the apple stuff,
    //though there may be something else going on
    SwingUtilities.invokeLater(() -> doLastAnimationAndExit());

  }
  
  /**
   * Uses shared, global variables:
   *   frameX, frameY, leftEyeX, leftEyeY, rightEyeX, rightEyeY,
   *   circleDiameter, circlePenThickness, mouseX, mouseY,
   *   eyeDiameter
   */
  private Point getPupilCoordinates(String leftOrRight)
  {
    int eyeX = 0;
    int eyeY = 0;
    if (leftOrRight.equals("left"))
    {
      eyeX = leftEyeX;
      eyeY = leftEyeY;
    }
    else
    {
      eyeX = rightEyeX;
      eyeY = rightEyeY;
    }

    // the left eye center on the entire screen
    int eyeCenterXLocal = eyeX + circleDiameter/2 + circlePenThickness;
    int eyeCenterYLocal = eyeY + circleDiameter/2 + circlePenThickness;
    int eyeCenterXUniversal = frameX + eyeCenterXLocal;
    int eyeCenterYUniversal = frameY + eyeCenterYLocal;

    // the universal X and Y distances ("universal" screen coordinates)
    // may go negative depending on screen positions.
    int difXU = mouseX - eyeCenterXUniversal;
    int difYU = mouseY - eyeCenterYUniversal;
    
    // calculate the distance/hypotenuse. use absolute values.
    int difXUAbs = Math.abs(difXU);
    int difYUAbs = Math.abs(difYU);
    int distUAbs = (int) Math.sqrt ((difXUAbs * difXUAbs) + (difYUAbs * difYUAbs));

    // the angle from the eye center to the mouse pointer
    double angleTheta = Math.atan2(difYU, difXU);
    int hypotenuseLocal = circleDiameter/2;

    int newEyeX = 0;
    int newEyeY = 0;
    if (hypotenuseLocal < distUAbs)
    {
      // LOCAL COORDINATES HERE
      // the mouse pointer is outside the eye.
      // know the hypotenuse and the angle, determine the x and y positions in the
      // local coordinate system.
      // opposite and adjacent should always be positive numbers, or zero.
      int opposite = (int) Math.round(Math.sin(angleTheta) * hypotenuseLocal);
      int adjacent = (int) Math.round(Math.cos(angleTheta) * hypotenuseLocal);
      int eyeXDist = adjacent;
      int eyeYDist = opposite;

      // angleTheta simplified these calcs; no worry about negative numbers
      newEyeX = eyeCenterXLocal + eyeXDist;
      newEyeY = eyeCenterYLocal + eyeYDist;

      // PROBABLY WANT TO THINK ABOUT THIS AS FOUR DIFFERENT QUADRANTS.
      // OR, CALCULATE THE CENTER OF THE CIRCLE, AND FIX THE DRAWING PROGRAM.
      // SKIPPING IT ANY MORE FOR NOW.
      // now adjust the coordinates b/c the updateEyes method wants the upper-left corner of the eye position
      if (newEyeX > eyeCenterXLocal) newEyeX = newEyeX - pupilDiameter;
      if (newEyeY > eyeCenterYLocal) newEyeY = newEyeY - pupilDiameter;
      return new Point (newEyeX, newEyeY);
    }
    else
    {
      // the mouse pointer is inside the eye, convert mouseX and mouseY to local coordinates
      // TODO i didn't think about this very hard (tired)
      newEyeX = eyeCenterXLocal + difXU;
      newEyeY = eyeCenterYLocal + difYU;
      return new Point (newEyeX, newEyeY);
    }    
  }
  
  private void getCurrentMouseLocation()
  {
    Point p = MouseInfo.getPointerInfo().getLocation();
    mouseX = p.x;
    mouseY = p.y;
  }
  
  private int getSleepTime()
  {
    if (mouseX == lastMouseX && mouseY == lastMouseY)
    {
      return SLEEP_TIME_MOUSE_NOT_MOVING;  // mouse position is the same
    }
    else
    {
      return SLEEP_TIME_MOUSE_MOVING;      // mouse is on the move
    }
  }


  /**
   * 
   * Below here are utility methods, which probably should be moved to a Mac Swing Utilities jar file.
   * -------------------------------------------------------------------------------------------------
   */

  private void sleep(long sleepTime)
  {
    try
    {
      Thread.sleep(sleepTime);
    }
    catch (InterruptedException e)
    {
    }
  }

  /**
   * If the app is not running on mac os x, die right away.
   */
  private void dieIfNotRunningOnMacOsX()
  {
//    boolean mrjVersionExists = System.getProperty("mrj.version") != null;
//    boolean osNameExists = System.getProperty("os.name").startsWith("Mac OS");
//
//    if ( !mrjVersionExists || !osNameExists)
//    {
//      System.err.println("Not running on a Mac OS X system.");
//      System.exit(1);
//    }
  }

  /**
   * You can use the Dimension object like this:
   * 
   * screenHeight = screenSize.height;
   * screenWidth = screenSize.width;
   */
  private Dimension getScreenSize()
  {
    return Toolkit.getDefaultToolkit().getScreenSize();
  }
  
  /**
   * Do everything we need to configure the app for Mac OS X systems.
   */
  private void configureForMacOSX()
  {
    // set some mac-specific properties; helps when i don't use ant to build the code
    System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", APP_NAME);

    // create an instance of the Mac Application class, so i can handle the 
    // mac quit event with the Mac ApplicationAdapter
//    Application macApplication = Application.getApplication();
//    MyApplicationAdapter macAdapter = new MyApplicationAdapter(this);
//    macApplication.addApplicationListener(macAdapter);
    
    // must enable the preferences option manually
    //macApplication.setEnabledPreferencesMenu(true);
    
  }

  public void handleQuitSignal()
  {
    inRunMode = false;
  }

  /**
   * Do one last 'fun' animation before quitting.
   */
  private void doLastAnimationAndExit()
  {
    final int leftPupilX = leftEyeX + (int)Math.round(circleDiameter/2.0) - (int)Math.round(pupilDiameter/2.0);
    final int leftPupilY = leftEyeY + (int)Math.round(circleDiameter/2.0) - (int)Math.round(pupilDiameter/2.0);
    final int rightPupilY = rightEyeY + (int)Math.round(circleDiameter/2.0) - (int)Math.round(pupilDiameter/2.0);
    final int rightPupilX = rightEyeX + (int)Math.round(circleDiameter/2.0) - (int)Math.round(pupilDiameter/2.0);

    eyesGlassPane.updateEyesAndRepaint(leftPupilX, leftPupilY, rightPupilX, rightPupilY);
    eyesGlassPane.repaint();

    sleep(SLEEP_TIME_BEFORE_QUITTING);
    System.exit(0);
  }
  

  public void makeFrameFullScreen()
  {
    if (inSmallDisplayMode)
    {
      makeFrameBigSize();
      inSmallDisplayMode = false;
    }
    else
    {
      makeFrameSmallSize();
      inSmallDisplayMode = true;
    }
  }

  public void makeFrameBigSize()
  {
    // TODO probably want the old location so you can switch back
    lastFrameLocation = new Point(frameX, frameY);
    
    Dimension screenSize = getScreenSize();
    int h = screenSize.height;
    int w = screenSize.width;
    
    // calculate all the dimensions
    frameHeight = h;
    frameWidth = w;

    int halfScreenWidth = w/2;                 
    int eyeDiameter = (int) Math.round(0.80 * halfScreenWidth);  // the diameter of each eye
    int eyeTopBottomPadding = (h - eyeDiameter) / 2;             // the padding of the top or bottom of the eye
    
    int xPaddingAroundEye = halfScreenWidth - eyeDiameter;
    int leftEyeLeftPadding = (int) ( 3.0f / 4.0f * xPaddingAroundEye);
    int leftEyeRightPadding = (int) ( 1.0f / 4.0f * xPaddingAroundEye);
    int rightEyeLeftPadding = leftEyeRightPadding;

    leftEyeX = leftEyeLeftPadding;
    leftEyeY = eyeTopBottomPadding;
    rightEyeX = halfScreenWidth + rightEyeLeftPadding;
    rightEyeY = eyeTopBottomPadding;

    circleDiameter = eyeDiameter;

    // values for the left and right eye
    circlePenThickness = LARGE_CIRCLE_PEN_THICKNESS;
    pupilDiameter = LARGE_PUPIL_DIAMETER;

    // calcs done, hide the current/small frame
    jEyesFrame.setVisible(false);

    // remove the transparency attribute
    AWTUtilities.setWindowOpacity(jEyesFrame, 1.0f);
    AWTUtilities.setWindowOpaque(jEyesFrame, true);

    // tell the panel about the new sizes
    jEyesPanel.setPenColor(Color.BLACK);
    jEyesPanel.setPenThickness(circlePenThickness);
    jEyesPanel.setEyeDiameter(eyeDiameter);
    jEyesPanel.setEyeSizeAndLocation(leftEyeX, leftEyeY, rightEyeX, rightEyeY);
    eyesGlassPane.setPupilDiameter(pupilDiameter);
    eyesGlassPane.setPupilColor(LARGE_PUPIL_PEN_COLOR);

    // re-display the frame here
    jEyesFrame.setBackgroundColorOfFace(LARGE_BACKGROUND_COLOR);
    jEyesFrame.display(frameWidth, frameHeight);
    frameX = jEyesFrame.getX();
    frameY = jEyesFrame.getX();
  }

  
  public void makeFrameSmallSize()
  {
    // calculate all the dimensions
    frameHeight = SMALL_FRAME_HEIGHT;
    frameWidth = SMALL_FRAME_WIDTH;

    leftEyeX = SMALL_LEFT_EYE_X;
    leftEyeY = SMALL_LEFT_EYE_Y;
    rightEyeX = SMALL_RIGHT_EYE_X;
    rightEyeY = SMALL_RIGHT_EYE_Y;
    circleDiameter = SMALL_CIRCLE_DIAMETER;
    circlePenThickness = SMALL_CIRCLE_PEN_THICKNESS;
    pupilDiameter = SMALL_PUPIL_DIAMETER;

    // hide the big frame
    jEyesFrame.setVisible(false);

    // remove the transparency attribute
    AWTUtilities.setWindowOpacity(jEyesFrame, 1.0f);
    AWTUtilities.setWindowOpaque(jEyesFrame, false);

    // tell the panel about the new sizes
    jEyesPanel.setPenColor(Color.BLACK);
    jEyesPanel.setPenThickness(circlePenThickness);
    jEyesPanel.setEyeDiameter(circleDiameter);
    jEyesPanel.setEyeSizeAndLocation(leftEyeX, leftEyeY, rightEyeX, rightEyeY);
    eyesGlassPane.setPupilDiameter(pupilDiameter);
    eyesGlassPane.setPupilColor(SMALL_PUPIL_PEN_COLOR);

    // re-display the frame here
    jEyesFrame.display(frameWidth, frameHeight);
    jEyesFrame.setLocation(lastFrameLocation);
    frameX = jEyesFrame.getX();
    frameY = jEyesFrame.getX();
  }
  
  
//  public void doPreferencesAction()
//  {
//  }

  public void handleAboutAction()
  {
    // create an html editor/renderer
    JEditorPane editor = new JEditorPane();
    editor.setContentType("text/html");
    editor.setEditable(false);
    editor.setSize(new Dimension(400,300));
    editor.setFont(UIManager.getFont("EditorPane.font"));
    // note: had to include this line to get it to use my font
    editor.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
    editor.setMargin(new Insets(5,15,25,15));
    editor.setText(ABOUT_DIALOG_MESSAGE);
    editor.setCaretPosition(0);
    JScrollPane scrollPane = new JScrollPane(editor);

    // add the hyperlink listener so the user can click my link
    // and go right to the website
    editor.addHyperlinkListener(new HyperlinkListener() {
    public void hyperlinkUpdate(HyperlinkEvent hev) 
    {
      if (hev.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
      {
        Runtime runtime = Runtime.getRuntime();
        String[] args = { "osascript", "-e", "open location \"" + APP_URL + "\"" };
        try
        {
          Process process = runtime.exec(args);
        }
        catch (IOException e)
        {
          // ignore this
        }
      }
    }});

    // i normally wouldn't make the reference 'null' here, but since the app is small and usually off
    // to the side, i have to do this to center the dialog on screen
    JOptionPane.showMessageDialog(null, scrollPane, "About Java Eyes", JOptionPane.INFORMATION_MESSAGE);
  }
}
