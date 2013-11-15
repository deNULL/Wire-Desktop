package ru.denull.wire;

import java.awt.Component;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.*;

public class InteractiveList extends JList implements MouseListener, MouseMotionListener {
  private static final long serialVersionUID = 3293889755508863611L;

  public InteractiveList() {
    super();
    addMouseListener(this);
    //addMouseMotionListener(this);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    forwardEvent(e);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    forwardEvent(e);
    
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    forwardEvent(e);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    forwardEvent(e);
  }

  @Override
  public void mouseExited(MouseEvent e) {
    forwardEvent(e);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    //forwardEvent(e);
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    forwardEvent(e);
  }
  
  private void forwardEvent(MouseEvent e) {
    int index = locationToIndex(e.getPoint());
    if (index == -1) return;
    
    ListCellRenderer renderer = getCellRenderer();
    if (renderer == null) return;
    
    Rectangle bounds = getCellBounds(index, index);
    if (bounds == null || !bounds.contains(e.getPoint())) return;
    
    ListModel model = getModel();
    if (model == null) return;
    
    ListSelectionModel selection = getSelectionModel();
    Component comp = renderer.getListCellRendererComponent(this, model.getElementAt(index), index, selection.isSelectedIndex(index), hasFocus() && selection.getLeadSelectionIndex() == index);
    
    //System.out.println("forw event at " + (e.getX() - bounds.x) + ", " + (e.getY() - bounds.y) + " (index " + index + ")");
    if (comp instanceof JComponent) {
      sendEvent((JComponent) comp, e, e.getX() - bounds.x, e.getY() - bounds.y);
    }
    
    repaint();
  }
  
  private void sendEvent(Component comp, MouseEvent e, int x, int y) {
    if (comp instanceof AbstractButton) {
      //((AbstractButton) comp).setSelected(true);
      MouseEvent ev = new MouseEvent(comp, e.getID(), e.getWhen(), e.getModifiers(), x, y, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
      comp.dispatchEvent(ev);
    } else
    if (comp instanceof Container) {
      for (Component child : ((Container) comp).getComponents()) {
        sendEvent(child, e, x - child.getX(), y - child.getY());
      }
    } else {
      if (comp instanceof AbstractButton) {
        System.out.println("found btn");
        ((AbstractButton) comp).setSelected(true);
      }
      MouseEvent ev = new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiers(), x, y, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton());
      comp.dispatchEvent(ev);
    }
  }
}
