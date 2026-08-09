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
 * The {@code PinchFilter} class is a specific implementation of the {@code TransformFilter}
 * that applies a pinch distortion effect to an image. This effect creates a stretching or
 * compressing of the image pixels based on their position relative to the center of the image.
 * <p>
 * The filter modifies the pixel coordinates by applying a sinusoidal transformation,
 * which distorts the image in a unique "pinch" style. The distortion effect is most
 * noticeable near the center of the image and diminishes outward.
 * <p>
 * This class overrides the {@code transformInverse} method to define the mapping of the
 * source image pixels to the resultant output image after distortion. It also provides
 * a custom string representation of the effect.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class PinchFilter extends TransformFilter {

	static final long serialVersionUID = -3768964940276766810L;

	public PinchFilter() {
	}

	@Override
	protected void transformInverse(int x, int y, float[] out) {
		int m = this.transformedSpace.width / 2;
		out[0] = x + (int) ((x - m) * Math.sin(Math.PI * (float) y / this.transformedSpace.height));
		out[1] = y;
	}

	@Override
	public String toString() {
		return "Distort/Pinch";
	}

}
