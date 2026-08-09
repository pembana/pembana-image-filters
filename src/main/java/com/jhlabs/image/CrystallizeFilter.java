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

/**
 * A filter which simulates crystallized patterns.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class CrystallizeFilter extends CellularFilter {

	private float edgeThickness = 0.4f;
	private boolean fadeEdges = false;
	private int edgeColor = 0xff000000;

	public CrystallizeFilter() {
		setScale(16);
		setRandomness(0.0f);
	}

	public void setEdgeThickness(float edgeThickness) {
		this.edgeThickness = edgeThickness;
	}

	public float getEdgeThickness() {
		return this.edgeThickness;
	}

	public void setFadeEdges(boolean fadeEdges) {
		this.fadeEdges = fadeEdges;
	}

	public boolean getFadeEdges() {
		return this.fadeEdges;
	}

	public void setEdgeColor(int edgeColor) {
		this.edgeColor = edgeColor;
	}

	public int getEdgeColor() {
		return this.edgeColor;
	}

	@Override
	public int getPixel(int x, int y, int[] inPixels, int width, int height) {
		float nx = this.m00 * x + this.m01 * y;
		float ny = this.m10 * x + this.m11 * y;
		nx /= this.scale;
		ny /= this.scale * this.stretch;
		nx += 1000;
		ny += 1000;    // Reduce artifacts around 0,0
		float f = evaluate(nx, ny);

		float f1 = this.results[0].distance;
		float f2 = this.results[1].distance;
		int srcx = ImageMath.clamp((int) ((this.results[0].x - 1000) * this.scale), 0, width - 1);
		int srcy = ImageMath.clamp((int) ((this.results[0].y - 1000) * this.scale), 0, height - 1);
		int v = inPixels[srcy * width + srcx];
		f = (f2 - f1) / this.edgeThickness;
		f = ImageMath.smoothStep(0, this.edgeThickness, f);
		if (this.fadeEdges) {
			srcx = ImageMath.clamp((int) ((this.results[1].x - 1000) * this.scale), 0, width - 1);
			srcy = ImageMath.clamp((int) ((this.results[1].y - 1000) * this.scale), 0, height - 1);
			int v2 = inPixels[srcy * width + srcx];
			v2 = ImageMath.mixColors(0.5f, v2, v);
			v = ImageMath.mixColors(f, v2, v);
		}
		else {
			v = ImageMath.mixColors(f, this.edgeColor, v);
		}
		return v;
	}

	@Override
	public String toString() {
		return "Stylize/Crystallize...";
	}

}
