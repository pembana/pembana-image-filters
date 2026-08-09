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

import java.awt.image.BufferedImage;

/**
 * A filter which simulates the appearance of looking through glass. A separate grayscale displacement image is provided and
 * pixels in the source image are displaced according to the gradient of the displacement map.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class DisplaceFilter extends TransformFilter {

	private float amount = 1;
	private BufferedImage displacementMap = null;
	private int[] xmap;
	private int[] ymap;
	private int dw;
	private int dh;

	public DisplaceFilter() {
	}

	public void setDisplacementMap(BufferedImage displacementMap) {
		this.displacementMap = displacementMap;
	}

	public BufferedImage getDisplacementMap() {
		return this.displacementMap;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getAmount() {
		return this.amount;
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dst) {
		int w = src.getWidth();
		int h = src.getHeight();

		BufferedImage dm = (this.displacementMap != null) ? this.displacementMap : src;

		this.dw = dm.getWidth();
		this.dh = dm.getHeight();

		int[] mapPixels = new int[this.dw * this.dh];
		getRGB(dm, 0, 0, this.dw, this.dh, mapPixels);
		this.xmap = new int[this.dw * this.dh];
		this.ymap = new int[this.dw * this.dh];

		int i = 0;
		for (int y = 0; y < this.dh; y++) {
			for (int x = 0; x < this.dw; x++) {
				int rgb = mapPixels[i];
				int r = (rgb >> 16) & 0xff;
				int g = (rgb >> 8) & 0xff;
				int b = rgb & 0xff;
				mapPixels[i] = (r + g + b) / 8; // An arbitrary scaling factor which gives a good range for "amount"
				i++;
			}
		}

		i = 0;
		for (int y = 0; y < this.dh; y++) {
			int j1 = ((y + this.dh - 1) % this.dh) * this.dw;
			int j2 = y * this.dw;
			int j3 = ((y + 1) % this.dh) * this.dw;
			for (int x = 0; x < this.dw; x++) {
				int k1 = (x + this.dw - 1) % this.dw;
				int k2 = x;
				int k3 = (x + 1) % this.dw;
				this.xmap[i] = mapPixels[k1 + j1] + mapPixels[k1 + j2] + mapPixels[k1 + j3] - mapPixels[k3 + j1] - mapPixels[k3 + j2] - mapPixels[k3 + j3];
				this.ymap[i] = mapPixels[k1 + j3] + mapPixels[k2 + j3] + mapPixels[k3 + j3] - mapPixels[k1 + j1] - mapPixels[k2 + j1] - mapPixels[k3 + j1];
				i++;
			}
		}
		mapPixels = null;
		dst = super.filter(src, dst);
		this.xmap = null;
		this.ymap = null;
		return dst;
	}

	@Override
	protected void transformInverse(int x, int y, float[] out) {
		float xDisplacement;
		float yDisplacement;
		float nx = x;
		float ny = y;
		int i = (y % this.dh) * this.dw + x % this.dw;
		out[0] = x + this.amount * this.xmap[i];
		out[1] = y + this.amount * this.ymap[i];
	}

	@Override
	public String toString() {
		return "Distort/Displace...";
	}
}
