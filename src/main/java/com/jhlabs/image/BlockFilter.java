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

import java.awt.Point;

/**
 * A Filter to pixellate images.
 *
 * @author Jerry Huxtable
 * @author Gunnar Hillert
 */
public class BlockFilter extends TransformFilter {

	static final long serialVersionUID = 8077109551486196569L;

	private int blockSize = 2;

	/**
	 * Set the pixel block size.
	 * @param blockSize the number of pixels along each block edge
	 */
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	/**
	 * Get the pixel block size.
	 * @return the number of pixels along each block edge
	 */
	public int getBlockSize() {
		return this.blockSize;
	}


	/**
	 * Construct a BlockFilter.
	 */
	public BlockFilter() {
	}

	protected void transform(int x, int y, Point out) {
		out.x = (x / this.blockSize) * this.blockSize;
		out.y = (y / this.blockSize) * this.blockSize;
	}

	@Override
	protected void transformInverse(int x, int y, float[] out) {
		out[0] = (x / this.blockSize) * this.blockSize;
		out[1] = (y / this.blockSize) * this.blockSize;
	}

	@Override
	public String toString() {
		return "Stylize/Mosaic...";
	}
}

