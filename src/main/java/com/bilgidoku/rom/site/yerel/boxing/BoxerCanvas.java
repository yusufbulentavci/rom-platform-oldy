package com.bilgidoku.rom.site.yerel.boxing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bilgidoku.rom.gwt.client.util.Layer;
import com.bilgidoku.rom.gwt.client.util.RomEntryPoint;
import com.bilgidoku.rom.min.Sistem;
import com.bilgidoku.rom.shared.RunException;
import com.bilgidoku.rom.shared.code.Code;
import com.bilgidoku.rom.shared.json.JSONObject;
import com.bilgidoku.rom.site.yerel.Ctrl;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;

public class BoxerCanvas implements RichTextFeedback {

    public static final CssColor CANVAS_BOX_RESIZE_CIRCLE_FILL_COLOR = CssColor.make("rgba(255, 120, 120, .4)");
    public static final CssColor CANVAS_FILL_COLOR = CssColor.make("rgba(255, 0, 0, .7)"); // red
    public static final CssColor CANVAS_BORDER_COLOR = CssColor.make("rgba(0, 255, 0, .4)"); // pink

    public static final CssColor SELECT_COLOR = CssColor.make("rgba(0, 0, 255, .4)"); // blue
    public static final CssColor HILI_COLOR = CssColor.make("rgba(0, 255, 255, .7)"); // CYAN

    public static final CssColor RECT_COLOR = CssColor.make("rgba(255,255,0, .0)"); // yellow

    private static final int SNAP_PIXEL = 5;

    private final Image imgCanvasHeight = new Image("/_local/images/common/resize_small.png");

    private final Canvas canvas = Canvas.createIfSupported();
    Map<String, Box> boxes = new HashMap<String, Box>();

    private boolean active = false;
    // private String editingItem = null;

    private int htmlLeft;
    private int htmlTop;
    private int htmlWidth;
    private int htmlHeight;

    boolean dragging = false;
    boolean maybeclick = false;
    int selectMouseOriginalx, selectMouseOriginaly;

    private final List<Box> selected = new ArrayList<Box>();
    int hitMode = 0;

    private final BoxHolder boxHolder;

    private Element root;

    boolean resizing = false;
    int resizeMouseDownY, resizeMouseDownHeight;

    private final Code code;
    protected boolean allowedToMove;

    boolean waitingdraw = false;

    public String toString() {
        return boxHolder.name() + " active:" + active + " waitingDraw:" + waitingdraw + " drag:" + dragging
                + " maybeclick:" + maybeclick + " resize:" + resizeMouseDownHeight + " allowedToMove:" + allowedToMove;
    }

    private class NotAvailable extends RuntimeException {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

    }

    public BoxerCanvas(BoxHolder feedback, AbsolutePanel panel, String rootElId, Code cs) throws RunException {
        this.boxHolder = feedback;
        this.code = cs;

        this.root = Document.get().getElementById(rootElId);
        if (root == null) {
            Window.alert(Ctrl.trans.notReady());
            return;
        }

        rootDims();

        Context2d context = getContext();
        context.save();

        canvas.setSize(htmlWidth + "px", htmlHeight + "px");
        canvas.setCoordinateSpaceWidth(htmlWidth);
        canvas.setCoordinateSpaceHeight(htmlHeight);

        canvas.getElement().getStyle().setZIndex(Layer.layer0);

        imgCanvasHeight.getElement().setDraggable(Element.DRAGGABLE_FALSE);
        imgCanvasHeight.getElement().setAttribute("unselectable", "on");
        imgCanvasHeight.getElement().setAttribute("draggable", "false");
        imgCanvasHeight.getElement().getStyle().setZIndex(5000);
        imgCanvasHeight.addStyleName("boxer-resize");
        imgCanvasHeight.getElement().setId("rsz" + rootElId);
        // imgCanvasHeight.setVisible(false);

        panel.add(imgCanvasHeight, htmlLeft + htmlWidth / 2, htmlTop + htmlHeight);

        panel.add(canvas, htmlLeft, htmlTop);

        forMouseMove();
        forMouseDown();
        forMouseUp();
        forMouseOver();
        forMouseOut();
        forDoubleClick();

        forArrowMouseDown();
        forArrowMouseUp();
        forArrowMouseMove();
        forArrowMouseOut();

        for (Code c : cs.children()) {
            String string = c.ensureId();

            Element e1 = Document.get().getElementById(string);
            if (e1 == null) {
                continue;
            }

            Box b = new Box(c, string, getLeft(e1), getTop(e1), getWidth(e1), getHeight(e1));
            boxes.put(string, b);

            if (b.isHtml()) {
                // e1.setAttribute("contenteditable", "true");
                // CkWrap.inlineOne(this, string);
            }
        }

    }

    private void showCanvasResize() {
        // imgCanvasHeight.setVisible(true);
        // Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
        // @Override
        // public boolean execute() {
        // imgCanvasHeight.setVisible(false);
        // return false;
        // }
        // }, 5000);
    }

    protected void rootDims() {
        htmlLeft = root.getAbsoluteLeft();
        htmlTop = root.getAbsoluteTop();

        htmlWidth = root.getClientWidth();
        htmlHeight = root.getClientHeight();

        if (htmlWidth == 0) {
            htmlWidth = 800;
        }
        if (htmlHeight == 0) {
            htmlHeight = 400;
        }
    }

    private void forArrowMouseOut() {
        imgCanvasHeight.addMouseOutHandler(new MouseOutHandler() {

            @Override
            public void onMouseOut(MouseOutEvent event) {
                event.preventDefault();

                try {
                    // end of resizing
                    resizing = false;
                    boxHolder.busy();

                    // drawnow("");
                } catch (NotAvailable ne) {

                }
            }
        });

    }

    private void forArrowMouseMove() {
        imgCanvasHeight.addMouseMoveHandler(new MouseMoveHandler() {

            @Override
            public void onMouseMove(MouseMoveEvent event) {
                event.preventDefault();

                try {
                    boxHolder.busy();
                    if (!resizing) {
                        return;
                    }

                    int rdy = event.getClientY() - resizeMouseDownY;

                    int nch = resizeMouseDownHeight + rdy;

                    setCanvasHeight(nch);

                    imgCanvasHeight.getElement().getStyle().setTop(htmlTop + htmlHeight, Unit.PX);

                    boxHolder.canvasResize();

                } catch (NotAvailable ne) {

                }
            }

        });

    }

    private void forArrowMouseUp() {
        imgCanvasHeight.addMouseUpHandler(new MouseUpHandler() {

            @Override
            public void onMouseUp(MouseUpEvent event) {
                event.preventDefault();
                resizing = false;
                dragging = false;
                idle();
                drawnow();
            }
        });

    }

    private void forArrowMouseDown() {
        imgCanvasHeight.addMouseDownHandler(new MouseDownHandler() {

            @Override
            public void onMouseDown(MouseDownEvent event) {
                event.preventDefault();
                try {
                    boxHolder.busy();
                    unselect();
                    resizing = true;
                    resizeMouseDownY = event.getClientY();
                    resizeMouseDownHeight = htmlHeight;
                } catch (NotAvailable ne) {

                }
            }
        });

    }

    protected void showMenu(int dx, int dy) {
        if (noSelected()) {
            return;
        }
        boxHolder.showMenu(dx, dy, firstSelected().id, firstSelected().getCode());

    }

    private Box firstSelected() {
        return selected.get(0);
    }

    protected boolean noSelected() {
        return selected.size() == 0;
    }

    private void forMouseOut() {
        canvas.addMouseOutHandler(new MouseOutHandler() {

            @Override
            public void onMouseOut(MouseOutEvent event) {
                event.preventDefault();
                try {
                    deactivate();
                    dragging = false;
                } catch (NotAvailable ne) {

                }
            }

        });

    }

    private void forMouseOver() {
        canvas.addMouseOverHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                event.preventDefault();
                try {
                    boxHolder.busy();
                    int dx = event.getRelativeX(root);
                    int dy = event.getRelativeY(root);
                    activate(dx, dy);
                } catch (NotAvailable ne) {

                }
            }
        });
    }

    private void forMouseUp() {
        canvas.addMouseUpHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                // event.preventDefault();
                try {

                    int dx = event.getRelativeX(root);
                    int dy = event.getRelativeY(root);

                    if (maybeclick) {
                        dragging = false;
                        checkSelected(dx, dy, false);
                        maybeclick = false;
                        // showMenu(event.getClientX(), event.getClientY() +
                        // Window.getScrollTop());
                    } else {
                        // end of resize ya da move
                        dragging = false;
                        // deactivate();
                        // unselect();
                        // idle();
                        // redraw();
                        boxHolder.setChanged(true);
                    }

                } catch (NotAvailable ne) {

                }
            }
        });

    }

    private void forDoubleClick() {
        canvas.addDoubleClickHandler(new DoubleClickHandler() {

            @Override
            public void onDoubleClick(DoubleClickEvent event) {
                event.preventDefault();
                if (noSelected()) {
                    return;
                }

                try {
                    // int dx = event.getRelativeX(root);
                    // int dy = event.getRelativeY(root);
                    dragging = false;
                    // selectOn(dx, dy);
                    showMenu(event.getClientX() + Window.getScrollLeft(), event.getClientY() + Window.getScrollTop());

                } catch (NotAvailable ne) {

                }

            }
        });

    }

    private void forMouseDown() {
        canvas.addMouseDownHandler(new MouseDownHandler() {

            @Override
            public void onMouseDown(MouseDownEvent event) {
                event.preventDefault();
                try {
//					int dx = event.getRelativeX(root);
//					int dy = event.getRelativeY(root);
                    dragging = true;
                    maybeclick = true;

//					selectOn(dx, dy);
                } catch (NotAvailable ne) {

                }
            }

        });

    }

    private void forMouseMove() {
        canvas.addMouseMoveHandler(new MouseMoveHandler() {

            @Override
            public void onMouseMove(MouseMoveEvent event) {
                if (!allowedToMove) {
                    return;
                }

                event.preventDefault();
                boxHolder.busy();
                if (maybeclick) {
                    int dx = event.getRelativeX(root);
                    int dy = event.getRelativeY(root);
                    checkSelected(dx, dy, true);
                }
                maybeclick = false;

                try {
                    int dx = event.getRelativeX(root);
                    int dy = event.getRelativeY(root);

                    moving(dx, dy);
                } catch (NotAvailable ne) {

                }
            }

        });

    }

    protected void posChanged() {
        if (selected == null) {
            return;
        }

        // Element e1 = Document.get().getElementById("i1");
        // e1.getParentElement().getStyle().setLeft(selected.boxLeft(),
        // Unit.PX);
        // e1.getParentElement().getStyle().setTop(selected.top, Unit.PX);
    }

    // private int getPadLeft(Element e1) {
    // return getInt(e1.getStyle().getPaddingLeft());
    // }
    //
    // private int getPadTop(Element e1) {
    // return getInt(e1.getStyle().getPaddingTop());
    // }
    //
    // private int getPadRight(Element e1) {
    // return getInt(e1.getStyle().getPaddingRight());
    // }
    //
    // private int getPadBottom(Element e1) {
    // return getInt(e1.getStyle().getPaddingBottom());
    // }
    private int getHeight(Element e1) {
        int h = 0;
        if (e1.getStyle().getHeight() != null && !e1.getStyle().getHeight().isEmpty()) {
            h = h + getInt(e1.getStyle().getHeight());
            return h;

        } else {
            h = h + e1.getClientHeight();
            return h;
        }
    }

    private int getWidth(Element e1) {
        int w = 0;

        if (e1.getStyle().getWidth() != null && !e1.getStyle().getWidth().isEmpty()) {
            w = w + getInt(e1.getStyle().getWidth());
            return w;
        } else {
            w = w + e1.getClientWidth();
            return w;

        }

    }

    private int getLeft(Element e1) {
        int w = 0;

        if (e1.getStyle().getLeft() != null && !e1.getStyle().getLeft().isEmpty()) {
            w = w + getInt(e1.getStyle().getLeft());
            return w;
        } else {
            w = w + e1.getOffsetLeft();
            return w;

        }

    }

    private int getTop(Element e1) {
        int w = 0;

        if (e1.getStyle().getTop() != null && !e1.getStyle().getTop().isEmpty()) {
            w = w + getInt(e1.getStyle().getTop());
            return w;
        } else {
            w = w + e1.getOffsetTop();
            return w;

        }

    }

    private int getInt(String valPx) {
        if (valPx == null) {
            return 0;
        }

        int i = 0;
        try {
            valPx = valPx.trim().toLowerCase().replace("px", "");
            if (valPx.indexOf(".") <= 0) {
                i = Integer.parseInt(valPx);
            } else {
                i = Integer.parseInt(valPx.substring(0, valPx.indexOf(".")));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return i;
    }

    protected int snapy(int i) {
        // Box fselected = firstSelected();
        // for (Box it : boxes.values()) {
        // if (it == fselected)
        // continue;
        //
        // if (Math.abs(it.boxTop() - i) < SNAP_PIXEL) {
        // return it.boxTop();
        // }
        //
        // if (Math.abs(it.boxBottom() - i) < SNAP_PIXEL) {
        // return it.boxBottom();
        // }
        //
        // if (Math.abs(fselected.boxHeight() + i - it.boxTop()) < SNAP_PIXEL) {
        // return it.boxTop() - fselected.boxHeight();
        // }
        //
        // if (Math.abs(fselected.boxHeight() + i - it.boxBottom()) <
        // SNAP_PIXEL) {
        // return it.boxBottom() - fselected.boxHeight();
        // }
        //
        // }
        return i;
    }

    protected int snapx(int i) {
        // Box fselected = firstSelected();
        //
        // if (Math.abs(-i) < SNAP_PIXEL) {
        // return 0;
        // }
        //
        // for (Box it : boxes.values()) {
        // if (it == fselected)
        // continue;
        //
        // if (Math.abs(it.boxLeft() - i) < SNAP_PIXEL) {
        // return it.boxLeft();
        // }
        //
        // if (Math.abs(it.boxRight() - i) < SNAP_PIXEL) {
        // return it.boxRight();
        // }
        //
        // if (Math.abs(fselected.boxWidth() + i - it.boxLeft()) < SNAP_PIXEL) {
        // return it.boxLeft() - fselected.boxWidth();
        // }
        //
        // if (Math.abs(fselected.boxWidth() + i - it.boxRight()) < SNAP_PIXEL)
        // {
        // return it.boxRight() - fselected.boxWidth();
        // }
        //
        // }
        return i;
    }

    private int hitMode(int dx, int dy) {
        for (Box it : boxes.values()) {
            int hm = it.hitMode(dx, dy);
            if (hm > 0) {
                return hm;
            }
        }
        return -1;
    }

    protected void checkSelected(int dx, int dy, boolean noRemove) {

        Box aday = null;
        int ahm = 0;

        for (Box it : boxes.values()) {
            int hm = it.hitMode(dx, dy);
            if (hm > 0 && (aday == null || it.smaller(aday))) {
                aday = it;
                ahm = hm;
            }
        }
        if (aday != null) {
            select(aday, ahm, dx, dy, noRemove);
            return;
        }

        if (!noRemove) {
            unselect();
        }
    }

    public void drawnow() {

        Context2d context = getContext();
        context.clearRect(0, 0, htmlWidth, htmlHeight);

        context.beginPath();
        context.setStrokeStyle(CANVAS_BORDER_COLOR);

        // context.fillText(toString(), 30, 30);
        context.stroke();

        // if (active) {
        // // context.beginPath();
        // // context.setFillStyle(CANVAS_FILL_COLOR);
        // // context.rect(0, 0, htmlWidth, htmlHeight);
        // // context.fillText(feedback.name(), 30, 30);
        // // context.fill();
        // } else {
        // // context.beginPath();
        // // context.setStrokeStyle(CANVAS_BORDER_COLOR);
        // // context.rect(0, 0, cw, ch);
        // // context.rect(1, 1, cw - 2, ch - 2);
        // // context.fillText(feedback.name(), 30, 30);
        // // context.stroke();
        // }
        for (Box it : boxes.values()) {
            if (selected.contains(it)) {
                drawSelected(context, it);
            } else {
                drawBox(context, it);
            }
        }

    }

    protected Context2d getContext() {
        try {
            Context2d c = canvas.getContext2d();
            return c;
        } catch (Exception e) {
            throw new NotAvailable();
        }
    }

    public void idle() {
        try {
            Context2d context = getContext();
            context.clearRect(0, 0, htmlWidth, htmlHeight);
        } catch (NotAvailable ne) {

        }

    }

    private void drawBox(Context2d context, Box it) {

        rect(context, it.boxLeft(), it.boxTop(), it.boxWidth(), it.boxHeight(), RECT_COLOR, RECT_COLOR);

        drawResizeBox(context, it);

        context.beginPath();

        // context.fillText(it.edit + "-" + dragging + "", it.boxLeft() + 10,
        // it.boxTop() + 45);
        context.fill();
        context.stroke();
    }

    private void drawSelected(Context2d context, Box it) {

        rect(context, it.boxLeft(), it.boxTop(), it.boxWidth(), it.boxHeight(), RECT_COLOR, SELECT_COLOR);
        drawResizeBox(context, it);

        context.beginPath();
        // context.fillText(it.boxWidth() + "-", it.boxLeft() + 10, it.boxTop()
        // + 45);
        context.fill();
        context.stroke();

    }

    protected void drawResizeBox(Context2d context, Box it) {
        // corners

        corner(context, it.boxLeft(), it.boxBottom() - 10, 0, 10, 10, 0, RECT_COLOR,
                CANVAS_BOX_RESIZE_CIRCLE_FILL_COLOR);

        corner(context, it.boxLeft(), it.boxTop() + 10, 0, -10, 10, 0, RECT_COLOR, CANVAS_BOX_RESIZE_CIRCLE_FILL_COLOR);

        corner(context, it.boxRight(), it.boxTop() + 10, 0, -10, -10, 0, RECT_COLOR,
                CANVAS_BOX_RESIZE_CIRCLE_FILL_COLOR);
        corner(context, it.boxRight(), it.boxBottom() - 10, 0, 10, -10, 0, RECT_COLOR,
                CANVAS_BOX_RESIZE_CIRCLE_FILL_COLOR);

        // middle points
        // sol orta
        circle(context, it.boxLeft(), it.boxYmid(), 2, RECT_COLOR, CANVAS_BOX_RESIZE_CIRCLE_FILL_COLOR);

        // sag orta -
        circle(context, it.boxRight(), it.boxYmid(), 2, RECT_COLOR, CANVAS_BOX_RESIZE_CIRCLE_FILL_COLOR);

        // ust orta
        circle(context, it.boxXmid(), it.boxTop(), 2, RECT_COLOR, CANVAS_BOX_RESIZE_CIRCLE_FILL_COLOR);

        // alt orta
        circle(context, it.boxXmid(), it.boxBottom(), 2, RECT_COLOR, CANVAS_BOX_RESIZE_CIRCLE_FILL_COLOR);
    }

    private void rect(Context2d context, int left, int top, int width, int height, CssColor stroke, CssColor fill) {
        context.beginPath();
        context.setFillStyle(fill);
        context.setStrokeStyle(stroke);

        context.rect(left, top, width, height);
        context.stroke();
        context.fill();

    }

    private void corner(Context2d context, int left, int top, int h1, int w1, int h2, int w2, CssColor stroke,
            CssColor fill) {
        context.beginPath();
        context.setFillStyle(fill);
        context.setStrokeStyle(stroke);

        context.moveTo(left, top);
        context.lineTo(left + h1, top + w1);
        context.lineTo(left + h1 + h2, top + w1 + w2);

        context.stroke();
        context.fill();

    }

    private void circle(Context2d context, int left, int top, int width, CssColor stroke, CssColor fill) {
        context.beginPath();
        context.setFillStyle(fill);
        context.setStrokeStyle(stroke);

        context.arc(left, top, width, 0, 2 * Math.PI);
        context.stroke();
        context.fill();

    }

    public void showCursor() {

    }

    public void deleted(String elId) {

    }

    protected void moving(int dx, int dy) {
        if (noSelected()) {
            RomEntryPoint.one.setCursor(Cursor.DEFAULT);
            return;
        }

        if (allowedToMove && !dragging) {
            int h = hitMode(dx, dy);
            Cursor c = Cursor.DEFAULT;
            if (h > 0) {
                switch (h) {
                    case Box.HITLEFT:
                        c = Cursor.W_RESIZE;
                        break;
                    case Box.HITLEFTTOP:
                        c = Cursor.NW_RESIZE;
                        break;
                    case Box.HITLEFTBOTTOM:
                        c = Cursor.SW_RESIZE;
                        break;

                    case Box.HITRIGTH:
                        c = Cursor.E_RESIZE;
                        break;
                    case Box.HITRIGTHTOP:
                        c = Cursor.NE_RESIZE;
                        break;
                    case Box.HITRIGTHBOTTOM:
                        c = Cursor.SE_RESIZE;
                        break;

                    case Box.HITTOP:
                        c = Cursor.N_RESIZE;
                    case Box.HITBOTTOM:
                        c = Cursor.S_RESIZE;
                        break;

                    case Box.HITCENTER:
                        c = Cursor.MOVE;
                        break;

                }
            }

            RomEntryPoint.one.setCursor(c);

            return;

        }

        if (hitMode == Box.HITCENTER) {
            for (Box it : selected) {
                it.setXy(snapx(it.originalx() + dx - selectMouseOriginalx),
                        snapy(it.originaly() + dy - selectMouseOriginaly));
            }

            posChanged();
        } else {
            Box fselected = firstSelected();

            if ((hitMode & Box.HITLEFT) > 0) {
                int nx = snapx(fselected.originalx() + dx - selectMouseOriginalx);
                int nw = nx - fselected.originalx();
                fselected.cx(nx, fselected.originalw() - nw);
            }
            if ((hitMode & Box.HITRIGTH) > 0) {
                int nw = snapx(fselected.originalw() + dx - selectMouseOriginalx);
                fselected.width(nw);
            }

            if ((hitMode & Box.HITTOP) > 0) {
                int ny = snapy(fselected.originaly() + dy - selectMouseOriginaly);
                int nh = ny - fselected.originaly();
                fselected.cy(ny, fselected.originalh() - nh);
            }

            if ((hitMode & Box.HITBOTTOM) > 0) {
                int ny = snapy(fselected.originalh() + dy - selectMouseOriginaly);
                fselected.height(ny);
            }
        }
        drawnow();
    }

    private void addBox(Tcs tcs, int dx, int dy) {
        dragging = true;

        DivElement d = Document.get().createDivElement();
        d.setInnerHTML(tcs.html);

        Element div = d.getFirstChildElement();
        div.getStyle().setLeft(dx, Unit.PX);
        div.getStyle().setTop(dy, Unit.PX);

        root.appendChild(div);

        String w = div.getStyle().getWidth();
        int iw = (w == null || w.length() == 0) ? 100 : Code.intPx(w);
        String h = div.getStyle().getHeight();
        int ih = (h == null || h.length() == 0) ? 100 : Code.intPx(h);

        String id = div.getId();

        Box box = new Box(tcs.code, id, dx, dy, iw, ih);
        boxes.put(id, box);

        if (box.isHtml()) {
            // div.setAttribute("contenteditable", "true");
            // CkWrap.inlineOne(this, id);
        }
        select(box, Box.HITCENTER, dx, dy, true);

    }

    // private String getStyle(Element div, String name) {
    // return div.getStyle().getProperty(name);
    // }
    protected void activate(int dx, int dy) {
        active = true;
        allowedToMove = true;
        try {
            Tcs s = boxHolder.popTransfer();
            if (s != null) {
                addBox(s, dx, dy);
            }

        } catch (RunException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void deactivate() {
        active = false;
        if (!noSelected() && dragging && hitMode == Box.HITCENTER) {
            Box sel = firstSelected();
            remove(sel);
            boxHolder.pushTransfer(sel.getCode());
            for (Box it : selected) {
                it.restoreDims();
            }

        }
        // unselect();
        idle();
        // drawnow();
    }

    private void unselect() {
        selected.clear();
        hitMode = -1;
        drawnow();
    }

    private void select(Box in, int hm, int dx, int dy, boolean noRemove) {
        // if (selected == in)
        // return;
        if (RomEntryPoint.one.getCtrlKey()) {
            if (selected.contains(in)) {
                if (!noRemove) {
                    selected.remove(in);
                }
            } else {
                selected.add(in);
            }
            if (selected.size() > 1) {
                hitMode = Box.HITCENTER;
            } else {
                hitMode = hm;
            }

        } else {
            if (!selected.contains(in)) {
                selected.clear();
                selected.add(in);
            }
            hitMode = hm;
        }

        selectMouseOriginalx = dx;
        selectMouseOriginaly = dy;

        if (hitMode == Box.HITCENTER) {
            for (Box it : boxes.values()) {
                if (it == in) {
                    continue;
                }
                if (in.isInside(it)) {
                    selected.add(it);
                }
            }
        }

        for (Box it : selected) {
            it.storeDims();
        }

        // feedback.selected(in, selected, hitMode);
        showCanvasResize();

        drawnow();
    }

    public com.google.gwt.json.client.JSONObject codes() throws RunException {
        this.code.childrenEmpty();
        for (Box b : boxes.values()) {
            this.code.addChild(b.getCode());
        }
        JSONObject s = this.code.store();
        return (com.google.gwt.json.client.JSONObject) s.ntv;
    }

    public void relocate() {
        try {
            unselect();
            rootDims();
            canvas.getElement().getStyle().setTop(htmlTop, Unit.PX);
            imgCanvasHeight.getElement().getStyle().setTop(htmlTop + htmlHeight, Unit.PX);

        } catch (NotAvailable ne) {

        }
    }

    public void widgetChanged(String id, String html) {

        Box b = boxes.get(id);
        if (b == null) {
            return;
        }

        b.getEl().removeFromParent();

        DivElement d = Document.get().createDivElement();
        d.setInnerHTML(html);

        Element div = d.getFirstChildElement();
        root.appendChild(div);

    }

    @Override
    public void htmlChanged(String id, List<Code> ret, String html) {
        try {
            Box editing = boxes.get(id);
            if (editing == null) {
                return;
            }
            // Box b = boxes.get(editing.id);
            DivElement el = (DivElement) Document.get().getElementById(editing.id);
            el.getStyle().setZIndex(0);
            el.setInnerHTML(html);

            int incHeight = editing.replaceCode(ret);

            if (incHeight > 0) {

                setCanvasHeight(htmlHeight + incHeight);

                boxHolder.canvasResize();
            }

            unselect();

        } catch (NotAvailable ne) {

        }
    }

    protected void setCanvasHeight(int newCanvasHeight) {
        htmlHeight = newCanvasHeight;
        root.getStyle().setHeight(newCanvasHeight, Unit.PX);
        canvas.setSize(htmlWidth + "px", htmlHeight + "px");
        canvas.setCoordinateSpaceHeight(htmlHeight);
        code.setPx("height", newCanvasHeight);
    }

    public void removeBox(String id) {
        Box b = boxes.get(id);
        remove(b);
        // redraw();
        // b.getEl().removeFromParent();
    }

    public void removeHtml(String id) {
        Box b = boxes.get(id);
        remove(b);
        // redraw();
        // b.getEl().removeFromParent();
    }

    private synchronized void remove(Box sel) {

        try {
            Element el = sel.getEl();
            el.removeFromParent();

        } catch (Exception e) {
            e.printStackTrace();
        }
        String id = sel.id;
        if (selected.contains(sel)) {
            selected.remove(sel);
        }
        boxes.remove(id);
    }

    public void editBox(String boxId) {

        if (noSelected()) {
            return;
        }

        Box fselected = firstSelected();

        if (fselected.isHtml()) {

            Box b = boxes.get(fselected.id);

            Element el = Document.get().getElementById(fselected.id);

            this.boxHolder.editHtml('h', fselected.getCode(), fselected.id, el.getInnerHTML());

            idle();

        } else {
            idle();
            if (fselected.getCode().tag.equals("w:uml")) {
                String uml;
                try {
                    uml = fselected.getCode().getParam("_uml");
                    this.boxHolder.editHtml('u', fselected.getCode(), fselected.id, uml);
                } catch (RunException e) {
                    Sistem.printStackTrace(e, "uml");
                }

            } else {
                this.boxHolder.editWidget(fselected.id, fselected.getCode());
            }
        }
    }

    public void widgetNotChanged(String id) {

    }

    public void styleBox(String boxId, Code code2) {
        if (noSelected()) {
            return;
        }

        Map<String, String> styles = code2.getStyleByType("defaultstyle");
        Box fselected = firstSelected();
        fselected.setStyle("borderWidth", "border-width", styles.get("border-width"));
        fselected.setStyle("borderColor", "border-color", styles.get("border-color"));
        fselected.setStyle("borderStyle", "border-style", styles.get("border-style"));
        fselected.setStyle("backgroundColor", "background-color", styles.get("background-color"));
        fselected.setStyle("color", "color", styles.get("color"));
        fselected.setStyle("paddingRight", "padding-right", styles.get("padding-right"));
        fselected.setStyle("paddingLeft", "padding-left", styles.get("padding-left"));
        fselected.setStyle("paddingTop", "padding-top", styles.get("padding-top"));
        fselected.setStyle("paddingBottom", "padding-bottom", styles.get("padding-bottom"));
        fselected.setStyle("borderRadius", "border-radius", styles.get("border-radius"));

    }

    public void redraw() {
        // TODO Auto-generated method stub

    }

    public Code getCodeOfBox(String boxId) {
        Box b = this.boxes.get(boxId);
        return b.getCode();
    }

    public void detach() {
        canvas.removeFromParent();
        imgCanvasHeight.removeFromParent();
    }

    public void selectAll() {
        hitMode = Box.HITCENTER;
        selectMouseOriginalx = 0;
        selectMouseOriginaly = 0;
        for (Box it : boxes.values()) {
            selected.add(it);
        }
        drawnow();
    }

}
