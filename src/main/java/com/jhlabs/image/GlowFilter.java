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
 * A filter which adds Gaussian blur to an image, producing a glowing effect.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class GlowFilter extends GaussianFilter {

	static final long serialVersionUID = 5377089073023183684L;

	private float amount = 0.5f;

	public GlowFilter() {
		this.radius = 2;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getAmount() {
		return this.amount;
	}

	@Override
	public BufferedImage filter(BufferedImage src, BufferedImage dst) {
		int width = src.getWidth();
		int height = src.getHeight();

		if (dst == null) {
			dst = createCompatibleDestImage(src, null);
		}

		int[] inPixels = new int[width * height];
		int[] outPixels = new int[width * height];
		src.getRGB(0, 0, width, height, inPixels, 0, width);

		if (this.radius > 0) {
			convolveAndTranspose(this.kernel, inPixels, outPixels, width, height, this.isAlpha(), CLAMP_EDGES);
			convolveAndTranspose(this.kernel, outPixels, inPixels, height, width, this.isAlpha(), CLAMP_EDGES);
		}

		src.getRGB(0, 0, width, height, outPixels, 0, width);

		float a = 4 * this.amount;

		int index = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb1 = outPixels[index];
				int r1 = (rgb1 >> 16) & 0xff;
				int g1 = (rgb1 >> 8) & 0xff;
				int b1 = rgb1 & 0xff;

				int rgb2 = inPixels[index];
				int r2 = (rgb2 >> 16) & 0xff;
				int g2 = (rgb2 >> 8) & 0xff;
				int b2 = rgb2 & 0xff;

				r1 = PixelUtils.clamp((int) (r1 + a * r2));
				g1 = PixelUtils.clamp((int) (g1 + a * g2));
				b1 = PixelUtils.clamp((int) (b1 + a * b2));

				inPixels[index] = (rgb1 & 0xff000000) | (r1 << 16) | (g1 << 8) | b1;
				index++;
			}
		}

		dst.setRGB(0, 0, width, height, inPixels, 0, width);
		return dst;
	}

	@Override
	public String toString() {
		return "Blur/Glow...";
	}
}
