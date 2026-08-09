/*
 * MIT License
 *
 * Copyright (c) 2025 Jerry Huxtable
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package com.jhlabs.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;

/**
 * The {@code MirrorFilter} class is a graphical image filter that creates a mirror effect
 * on an input image. The mirror effect can be customized by adjusting several properties
 * such as the reflection opacity, rotation, gap, and angle of the mirrored section.
 * <p>
 * This class extends {@code AbstractBufferedImageOp}, which provides convenience implementations
 * for some common BufferedImageOp methods.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class MirrorFilter extends AbstractBufferedImageOp {
	private float opacity = 1.0f;
	private float centreY = 0.5f;
	private float distance;
	private float angle;
	private float rotation;
	private float gap;

	public MirrorFilter() {
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getAngle() {
		return this.angle;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getDistance() {
		return this.distance;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getRotation() {
		return this.rotation;
	}

	public void setGap(float gap) {
		this.gap = gap;
	}

	public float getGap() {
		return this.gap;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}

	public float getOpacity() {
		return this.opacity;
	}

	public void setCentreY(float centreY) {
		this.centreY = centreY;
	}

	public float getCentreY() {
		return this.centreY;
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dst) {
		if (dst == null) {
			dst = createCompatibleDestImage(src, null);
		}
		BufferedImage tsrc = src;
		Shape clip;
		int width = src.getWidth();
		int height = src.getHeight();
		int h = (int) (this.centreY * height);
		int d = (int) (this.gap * height);

		Graphics2D g = dst.createGraphics();
		clip = g.getClip();
		g.clipRect(0, 0, width, h);
		g.drawRenderedImage(src, null);
		g.setClip(clip);
		g.clipRect(0, h + d, width, height - h - d);
		g.translate(0, 2 * h + d);
		g.scale(1, -1);
		g.drawRenderedImage(src, null);
		g.setPaint(new GradientPaint(0, 0, new Color(1.0f, 0.0f, 0.0f, 0.0f), 0, h, new Color(0.0f, 1.0f, 0.0f, this.opacity)));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
		g.fillRect(0, 0, width, h);
		g.setClip(clip);
		g.dispose();

		return dst;
	}

	@Override
	public String toString() {
		return "Effects/Mirror...";
	}
}
