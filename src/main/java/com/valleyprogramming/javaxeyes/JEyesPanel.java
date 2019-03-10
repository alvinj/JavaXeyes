package com.valleyprogramming.javaxeyes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class JEyesPanel extends JPanel
{
  private JavaXeyes controller;
  
  // eye information
  private int eyeDiameter, penThickness;
  private Color penColor;
  private Color backgroundColor = Color.DARK_GRAY;
  private int leftEyeX;
  private int leftEyeY;
  private int rightEyeX;
  private int rightEyeY;
  
  public JEyesPanel(JavaXeyes controller, int penThickness, Color penColor)
  {
    this.controller = controller;
    this.penThickness = penThickness;
    this.penColor = penColor;
  }

  /**
   * Approach #4.
   * From: http://oreilly.com/catalog/java2d/chapter/ch04.html
   * 
   * This works, and lets me control the line thickness.
   * If this is the only way to use the line thickness, use it.
   * Disadvantage: Makes centering harder.
   * --------------------------------------------------------------------
   */
  public void paintComponent(Graphics g) 
  {
    // no call to 'super', we're doing all the painting
    Graphics2D g2 = (Graphics2D)g;
    Ellipse2D leftEye = new Ellipse2D.Double(leftEyeX, leftEyeY, eyeDiameter, eyeDiameter);
    g2.setPaint(Color.WHITE);
    g2.fill(leftEye);
    g2.setStroke(new BasicStroke(penThickness));
    g2.setPaint(penColor);
    g2.draw(leftEye);

    Ellipse2D rightEye = new Ellipse2D.Double(rightEyeX, rightEyeY, eyeDiameter, eyeDiameter);
    g2.setPaint(Color.WHITE);
    g2.fill(rightEye);
    g2.setStroke(new BasicStroke(penThickness));
    g2.setPaint(penColor);
    g2.draw(rightEye);
  }
  
  public void setEyeSizeAndLocation(int leftEyeX, int leftEyeY, int rightEyeX, int rightEyeY)
  {
    this.leftEyeX = leftEyeX;
    this.leftEyeY = leftEyeY;
    this.rightEyeX = rightEyeX;
    this.rightEyeY = rightEyeY;
  }

  public void setBackgroundColor(Color backgroundColor)
  {
    this.backgroundColor = backgroundColor;
  }

  public void setPenColor(Color penColor)
  {
    this.penColor = penColor;
  }

  public void setPenThickness(int penThickness)
  {
    this.penThickness = penThickness;
  }
  
  public void setEyeDiameter(int eyeDiameter)
  {
    this.eyeDiameter = eyeDiameter;
  }
  
}


