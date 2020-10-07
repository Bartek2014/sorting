package sorts;

import templates.Sort;
import utils.Delays;
import utils.Highlights;
import utils.Reads;
import utils.Writes;

/*
 * 
MIT License

Copyright (c) 2020 Gaming32

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 *
 */

final public class OutOfPlaceSelectionSort extends Sort {  
    public OutOfPlaceSelectionSort(Delays delayOps, Highlights markOps, Reads readOps, Writes writeOps) {
        super(delayOps, markOps, readOps, writeOps);
        
        this.setSortPromptID("Out-of-Place Selection");
        this.setRunAllID("Out-Of-Place Selection Sort");
        this.setReportSortID("Out-Of-Place Selection Sort");
        this.setCategory("Selection Sorts");
        this.isComparisonBased(true);
        this.isBucketSort(false);
        this.isRadixSort(false);
        this.isUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.isBogoSort(false);
    }
    
    @Override
    public void runSort(int[] array, int length, int bucketCount) {
        int[] result = new int[length];
        int[] indices = new int[length];
        for (int i = 0; i < length; i++) {
            indices[i] = i;
        }

        for (int nxt = 0; nxt < length; nxt++) {
            Highlights.markArray(1, nxt);

            int lowest = Integer.MAX_VALUE;
            int lowestindex = 0;
            
            // for (int j = i + 1; j < length; j++) {
            //     Highlights.markArray(2, j);
            //     Delays.sleep(0.01);
                
            //     if (Reads.compare(array[j], array[lowestindex]) == -1){
            //         lowestindex = j;
            //         Highlights.markArray(1, lowestindex);
            //         Delays.sleep(0.01);
            //     }
            // }
            // Writes.swap(array, i, lowestindex, 0.02, true, false);
            for (int i = 0; i < length; i++) {
                Delays.sleep(0.001);
                int index = indices[i];
                if (index != -1) {
                    Highlights.markArray(2, i);
                    if (Reads.compare(array[i], lowest) == -1) {
                        lowest = array[i];
                        lowestindex = i;
                    }
                }
            }
            Highlights.clearMark(2);
            Writes.write(result, nxt, lowest, 0, false, true);
            Writes.write(indices, lowestindex, -1, 0, false, true);
        }

        // At this point we could just return result, but we need to render the result to the visualizer
        for (int i = 0; i < length; i++) {
            Writes.write(array, i, result[i], 0.667, true, false);
        }
    }
}