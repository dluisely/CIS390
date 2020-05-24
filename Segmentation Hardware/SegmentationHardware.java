/**
 * Segmentation Hardware algorithm
 *
 * @author Luisely Doza
 * @version April 27, 2020
 */
public class SegmentationHardware {
    int[][] segmentTable;
    
    public SegmentationHardware() {
        segmentTable = new int[][] {{ 100, 500 },
            { 2100, 200 },
            { 1500, 400 },
            { 900, 300 },
            { 2500, 1000 }
        };
    }
    
    
    public static void main() {
        SegmentationHardware sh = new SegmentationHardware();
        sh.printTable();
        
        
        sh.segmentation(4, 302);
        sh.segmentation(2, 105);
        sh.segmentation(1, 500);
    }
    
    public void printTable() {
        System.out.println("Segment Number       Segment Base       Segment Limit");
        for(int i = 0; i < segmentTable.length; i++) {
            System.out.printf("%-20d %-18d %d\n", i + 1, 
                    segmentTable[i][0], segmentTable[i][1]);
        }
    }
    
    public void segmentation(int segmentNum, int offset) {
        int physicalAddress;
        if (offset < segmentTable[segmentNum - 1][1]) {
            physicalAddress = segmentTable[segmentNum - 1][0] + offset;
            System.out.printf("\n%d < %d   OK   Physical Address is %d", 
                    offset, segmentTable[segmentNum - 1][1], physicalAddress);
        } else {
            System.out.printf("\n%d < %d   X   Trap: Addressing Error!", 
                    offset, segmentTable[segmentNum - 1][1]);
        }
    }
}
