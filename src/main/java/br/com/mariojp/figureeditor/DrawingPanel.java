
package br.com.mariojp.figureeditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {

    private static class Figure {
        Rectangle rect;
        Color color;

        Figure(Rectangle rect, Color color) {
            this.rect = rect;
            this.color = color;
        }
    }

    private List<Figure> figures = new ArrayList<>();
    private Figure selected = null;
    private Point dragStart = null;
    private Rectangle previewRect = null;
    private Color currentColor = Color.BLACK;

    public DrawingPanel() {
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Figure f : figures) {
                    if (f.rect.contains(e.getPoint())) {
                        selected = f;
                        dragStart = e.getPoint();
                        return;
                    }
                }
                dragStart = e.getPoint();
                previewRect = new Rectangle(dragStart);
                selected = null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                if (selected != null && dragStart != null) {
                    int dx = e.getX() - dragStart.x;
                    int dy = e.getY() - dragStart.y;
                    selected.rect.translate(dx, dy);
                    dragStart = e.getPoint();

                } else if (previewRect != null) {
                    previewRect.setBounds(
                            Math.min(dragStart.x, e.getX()),
                            Math.min(dragStart.y, e.getY()),
                            Math.abs(e.getX() - dragStart.x),
                            Math.abs(e.getY() - dragStart.y)
                    );
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (previewRect != null && previewRect.width > 0 && previewRect.height > 0) {
                    figures.add(new Figure(new Rectangle(previewRect), currentColor));
                }
                previewRect = null;
                dragStart = null;
                repaint();
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public void setCurrentColor(Color c) {
        this.currentColor = c;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Figure f : figures) {
            g.setColor(f.color);
            g.fillRect(f.rect.x, f.rect.y, f.rect.width, f.rect.height);
        }

        if (previewRect != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(currentColor);
            float[] dash = {4f, 4f};
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 1f, dash, 0f));
            g2.draw(previewRect);
        }
    }
}
