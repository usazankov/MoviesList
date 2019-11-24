/*
 *  Copyright 2018 Soojeong Shin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package ru.sample.movies.presentation.view.utils;

public final class Constant {

    private Constant() {
        // Restrict instantiation
    }

    /** Constant for the span count in the grid layout manager */
    public static final int GRID_SPAN_COUNT = 3;
    /** Constant for the grid spacing (px)*/
    public static final int GRID_SPACING = 8;
    /** True when including edge */
    public static final boolean GRID_INCLUDE_EDGE = true;

    /** Constant used to make ImageView 3:2 aspect ratio or 2:3 aspect ratio */
    static final int TWO = 2;
    static final int THREE = 3;

    /** Constant used in GridSpacingItemDecoration */
    public static final int ONE = 1;

    /** Size hint for initial load of PagedList */
    public static final int INITIAL_LOAD_SIZE_HINT = 100;
    /** Size of each page loaded by the PagedList */
    public static final int PAGE_SIZE = 100;
    /** Prefetch distance which defines how far ahead to load */
    public static final int PREFETCH_DISTANCE = 50;

}
