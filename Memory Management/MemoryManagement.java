/**
 * 
 * Given six memory partitions of 300 KB, 600 KB, 350 KB, 200 KB, 750 KB, and 125 KB (in order), 
 * how would the first-fit, best-fit, and worst-fit algorithms place 
 * processes of size 115 KB, 500 KB, 358 KB, 200 KB, and 375 KB (in order)?
 *
 * @author Luisely Doza
 * @version April 27, 2020
 */
public class MemoryManagement {
    int[] partitions;
    int[] processSizes;
    
    public static void main(String ars[]) {
        System.out.println("Given six memory partitions of 300 KB, 600 KB, 350 KB, 200 KB, 750 KB, and 125 KB (in order),");
        System.out.println("how would the first-fit, best-fit, and worst-fit algorithms place");
        System.out.println("processes of size 115 KB, 500 KB, 358 KB, 200 KB, and 375 KB (in order)?\n");
        
        MemoryManagement ff = new MemoryManagement();
        ff.initData();
        ff.firstFit();
        
        MemoryManagement bf = new MemoryManagement();
        bf.initData();
        bf.bestFit();
        
        MemoryManagement wf = new MemoryManagement();
        wf.initData();
        wf.worstFit();
    }
    
    public void initData() {
        partitions = new int[] {300, 600, 350, 200, 750, 125};
        processSizes = new int[] {115, 500, 358, 200, 375};
    }
    
    /**
     * Allocate the first hole that is big enough. 
     * Searching can start either at the beginning of the set of holes or at the location where the previous first-fit search ended. 
     * We can stop searching as soon as we find a free hole that is large enough.
     */
    public void firstFit() { 
        System.out.println("First Fit:");
        for (int i = 0; i < processSizes.length; i++) {
           for (int j = 0; j < partitions.length; j++) {
               if (processSizes[i] <= partitions[j]) {
                   System.out.printf("%d KB is put in %d-KB partition", processSizes[i], partitions[j]);
                   partitions[j] -= processSizes[i];
                   break;
               }
           }
           System.out.print(", leaving ");
           for (int s: partitions) {
               System.out.print(s + " KB ");
           }
           System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Allocate the smallest hole that is big enough. 
     * We must search the entire list, unless the list is ordered by size. 
     * This strategy produces the smallest leftover hole.
     */
    public void bestFit() {
        System.out.println("Best Fit:");
        for (int i = 0; i < processSizes.length; i++) {
            int smallest = Integer.MAX_VALUE;
            int index = 0;
            for (int j = 1; j < partitions.length; j++) {
                if (processSizes[i] <= partitions[j] && partitions[j] < smallest) {
                    index = j;
                    smallest = partitions[j];
                }
            }
            allocation(i, index);
        }
        System.out.println();
    }
    
    /**
     * Allocate the largest hole. 
     * Again, we must search the entire list, unless it is sorted by size. 
     * This strategy produces the largest leftover hole, which may be more useful than the smaller leftover hole from a best-fit approach.
     */
    public void worstFit() {
        System.out.println("Worst Fit:");
        for (int i = 0; i < processSizes.length; i++) {
            int largest = Integer.MIN_VALUE;
            int index = 0;
            for (int j = 1; j < partitions.length; j++) {
                if (processSizes[i] <= partitions[j] && partitions[j] > largest) {
                    index = j;
                    largest = partitions[j];
                }
            }
            allocation(i, index);
        }
        System.out.println();
    }
    
    public void allocation(int i, int index) {
        if(processSizes[i] > partitions[index]) {
            System.out.printf("%d KB must wait", processSizes[i]);
        } else {
            System.out.printf("%d KB is put in %d-KB partition", processSizes[i], partitions[index]);
            partitions[index] -= processSizes[i];
            System.out.print(", leaving ");
            for (int s: partitions) {
                System.out.print(s + " KB ");
            }
        }
        System.out.println();
    }
}
