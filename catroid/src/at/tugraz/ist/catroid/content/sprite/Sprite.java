/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010  Catroid development team 
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.tugraz.ist.catroid.content.sprite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import at.tugraz.ist.catroid.content.script.Script;

public class Sprite implements Serializable, Comparable<Sprite> {
    private static final long serialVersionUID = 1L;
    private String name;
    private int xPosition;
    private int yPosition;
    private int zPosition;
    private double scale;
    private boolean isVisible;
    private boolean toDraw;
    private List<Costume> costumeList;
    private List<Script> scriptList;
    private List<Thread> threadList;
    private Costume currentCostume;

    private void init() {
        zPosition = 0;
        scale = 100.0;
        isVisible = true;
        costumeList = new ArrayList<Costume>();
        scriptList = new ArrayList<Script>();
        threadList = new ArrayList<Thread>();
        currentCostume = null;
    }

    public Sprite(String name) {
        this.name = name;
        xPosition = 0;
        yPosition = 0;
        toDraw = false;
        init();
    }

    public Sprite(String name, int xPosition, int yPosition) {
        this.name = name;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        toDraw = false;
        init();
    }

    public void startScripts() {
        for (Script s : scriptList) {
            if (!s.isTouchScript()) {
                startScript(s);
            }
        }
    }

    private void startTouchScripts() {
        for (Script s : scriptList) {
            if (s.isTouchScript()) {
                startScript(s);
            }
        }
    }

    private void startScript(Script s) {
        final Script script = s;
        Thread t = new Thread(new Runnable() {
            public void run() {
                script.run();
            }
        });
        threadList.add(t);
        t.start();
    }

    public void pause() {
        for (Script s : scriptList) {
            s.setPaused(true);
        }
        for (Thread t : threadList) {
            t.interrupt();
        }
        threadList.clear();
    }

    public void resume() {
        for (Script s : scriptList) {
            s.setPaused(false);
            if (s.isTouchScript() && s.isFinished()) {
                continue;
            }
            startScript(s);
        }
    }

    public String getName() {
        return name;
    }

	public void setName(String name) {
		this.name = name;
	}

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public int getZPosition() {
        return zPosition;
    }

    public double getScale() {
        return scale;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public synchronized void setXYPosition(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        for (Costume costume : costumeList) {
            costume.setDrawPosition();
        }
        //		if (currentCostume != null) {
        //			currentCostume.setDrawPosition(); //TODO set all sprites in spriteList or only current?
        //		}
        toDraw = true;
    }

    public synchronized void setZPosition(int zPosition) {
        this.zPosition = zPosition;
        toDraw = true;
    }

    public void setScale(double scale) {
        if (scale <= 0.0) {
            throw new IllegalArgumentException("Sprite scale must be greater than zero!");
        }
        this.scale = scale;
        toDraw = true;
    }

    public void show() {
        isVisible = true;
        toDraw = true;
    }

    public void hide() {
        isVisible = false;
        toDraw = true;
    }

    public Costume getCurrentCostume() {
        return currentCostume;
    }

    public List<Costume> getCostumeList() {
        return costumeList;
    }

    public List<Script> getScriptList() {
        return scriptList;
    }

    public boolean getToDraw() {
        return toDraw;
    }

    public void setToDraw(boolean value) {
        toDraw = value;
    }

    public void setCurrentCostume(Costume costume) throws IllegalArgumentException {
        if (!costumeList.contains(costume)) {
            throw new IllegalArgumentException("Selected costume is not contained in Costume list of this sprite.");
        }
        currentCostume = costume;
        toDraw = true;
    }

    public int compareTo(Sprite sprite) {
        long thisZValue = getZPosition();
        long otherZValue = sprite.getZPosition();
        long difference = thisZValue - otherZValue;
        if (difference > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) difference;
    }

    public void processOnTouch(int coordX, int coordY) {
        if(currentCostume == null) {
            startTouchScripts();
            return;
        }
        int inSpriteCoordX = coordX - currentCostume.getDrawPositionX();
        int inSpriteCoordY = coordY - currentCostume.getDrawPositionY();

        //TODO: this is dirty, find a better solution than getting the Bitmap here
        Bitmap tempBitmap = currentCostume.getBitmap();
        if (inSpriteCoordX < 0 || inSpriteCoordX >= tempBitmap.getWidth()) {
            return;
        }
        if (inSpriteCoordY < 0 || inSpriteCoordY >= tempBitmap.getHeight()) {
            return;
        }
        tempBitmap.recycle();
//        if (mDrawObject.getBitmap() == null TODO: I am 12 and what is this?
//                || Color.alpha(mDrawObject.getBitmap().getPixel(inSpriteCoordX, inSpriteCoordY)) <= 10) {
//            return;
//        }

        startTouchScripts();
    }

    @Override
    public String toString() {
        return name;
    }
}
