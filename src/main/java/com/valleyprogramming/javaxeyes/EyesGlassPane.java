package com.valleyprogramming.javaxeyes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public class EyesGlassPane extends JComponent
{
  private JavaXeyes controller;
  private int x, y, diameter;
  private int leftEyeX, leftEyeY, rightEyeX, rightEyeY;
  private Color pupilColor = Color.DARK_GRAY;
  
  public EyesGlassPane(JavaXeyes controller)
  {
    this.controller = controller;
  }

  public void setPupilDiameter(int diameter)
  {
    this.diameter = diameter;
  }
  
  // this method draws the eyes, starting with coordinates at the upper-left corner.
  protected void paintComponent(Graphics g)
  {
    Graphics2D g2d = (Graphics2D)g;
    g2d.setPaint(pupilColor);

    // left eye
    Ellipse2D.Double leftEye = new Ellipse2D.Double(leftEyeX, leftEyeY, diameter, diameter);
    g2d.fill(leftEye);

    // right eye
    Ellipse2D.Double rightEye = new Ellipse2D.Double(rightEyeX,rightEyeY, diameter, diameter);
    g2d.fill(rightEye);
  }

  public void updateEyes(int leftEyeX, int leftEyeY, int rightEyeX, int rightEyeY)
  {
    this.leftEyeX = leftEyeX;
    this.leftEyeY = leftEyeY;
    this.rightEyeX = rightEyeX;
    this.rightEyeY = rightEyeY;
  }

  public void updateEyesAndRepaint(int leftEyeX, int leftEyeY, int rightEyeX, int rightEyeY)
  {
    this.leftEyeX = leftEyeX;
    this.leftEyeY = leftEyeY;
    this.rightEyeX = rightEyeX;
    this.rightEyeY = rightEyeY;
    repaint();
  }

  public void updateLocation(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  public void updateLocation(int x, int y, int h, int w)
  {
    this.x = x;
    this.y = y;
    this.diameter = h;
    this.diameter = w;
  }

  public void setX(int x)
  {
    this.x = x;
  }
  
  public void setY(int y)
  {
    this.y = y;
  }
  
  // TODO legacy, refactor
  public void setH(int h)
  {
    this.diameter = h;
  }
  
  // TODO legacy, refactor
  public void setW(int w)
  {
    this.diameter = w;
  }

  public void setPupilColor(Color pupilColor)
  {
    this.pupilColor = pupilColor;
  }
  
}
