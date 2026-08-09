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

import com.jhlabs.math.Function2D;

/**
 * The MapFilter class is used to distort image coordinates by mapping them through
 * specified two-dimensional functions. It extends the {@link TransformFilter} class and
 * allows custom transformations of the x and y coordinates of an image pixel.
 * <p>
 * The two-dimensional mapping functions for x and y coordinates are provided as
 * instances of the {@code Function2D} interface, which must implement a method
 * to evaluate the mapping based on input coordinates.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class MapFilter extends TransformFilter {

	private Function2D xMapFunction;
	private Function2D yMapFunction;

	public MapFilter() {
	}

	public void setXMapFunction(Function2D xMapFunction) {
		this.xMapFunction = xMapFunction;
	}

	public Function2D getXMapFunction() {
		return this.xMapFunction;
	}

	public void setYMapFunction(Function2D yMapFunction) {
		this.yMapFunction = yMapFunction;
	}

	public Function2D getYMapFunction() {
		return this.yMapFunction;
	}

	@Override
	protected void transformInverse(int x, int y, float[] out) {
		float xMap;
		float yMap;
		xMap = this.xMapFunction.evaluate(x, y);
		yMap = this.yMapFunction.evaluate(x, y);
		out[0] = xMap * this.transformedSpace.width;
		out[1] = yMap * this.transformedSpace.height;
	}

	@Override
	public String toString() {
		return "Distort/Map Coordinates...";
	}
}
