/**
 * Banker algorithm including safety and resources request algorithm.
 *
 * @author Luisely Doza
 * @version April 27, 2020
 */
public class Banker {
    int processes = 5;
    int resources = 3;
    int count = 0;
    int[] available;
    int[][] max;
    int[][] allocation;
    int[][] need = new int[processes][resources];
    int[] safeSequence = new int[processes];
    
    public void initData() {
        allocation = new int[][] { { 0,1,0 }, //P0
            { 2,0,0 }, //P1
            { 3,0,2 }, //P2
            { 2,1,1 }, //P3
            { 0,0,2}}; //P4
            
        max = new int[][] { { 7,5,3 },
            { 3,2,2 },
            { 9,0,2 },
            { 2,2,2 },
            { 4,3,3 }};
            
        available = new int[] { 3,3,2 };
    }
    
    public void calcNeed() {
        for (int i = 0; i < processes; i++) {
            for (int j = 0; j < resources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
    }
    
    public static void main(String args[]) {
        Banker original = new Banker();
        original.initData();
        original.calcNeed();
        System.out.println("No Request:");
        original.safetyAlgorithm();
        
        System.out.println("\n");
        
        int[] safeRequest = new int[] { 1,0,2 };
        System.out.println("Safe Request:");
        System.out.println("Request (1,0,2) for P1");
        Banker safe = new Banker();
        safe.initData();
        safe.calcNeed();
        safe.resourceRequestAlgorithm(1, safeRequest);
        
        System.out.println("\n");
        
        int[] unsafeRequest = new int[] { 3,3,0 };
        System.out.println("Unsafe Request:");
        System.out.println("Request (3,3,0) for P4");
        Banker unsafe = new Banker();
        unsafe.initData();
        unsafe.calcNeed();
        unsafe.resourceRequestAlgorithm(4, unsafeRequest);
    } 
    
    public void printTable() {
        System.out.println("Process    Allocation        Max           Need          Available");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("            A  B  C        A  B  C        A  B  C         A  B  C ");
        
        for(int i = 0; i < processes; i++) {
            System.out.print("  P" + (i));
            System.out.print("        ");
            for (int j = 0; j < resources; j++) {
                System.out.printf("%d  ", allocation[i][j]);
            }
            System.out.print("      ");
            for (int j = 0; j < resources; j++) {
                System.out.printf("%d  ", max[i][j]);
            }
            System.out.print("      ");
            for (int j = 0; j < resources; j++) {
                System.out.printf("%d  ", need[i][j]);
            }
            
            if (i == 0) {
                System.out.print("       ");
                for (int j = 0; j < resources; j++) {
                    System.out.printf("%d  ", available[j]);
                }
            }
           
            System.out.println();
        }
        System.out.println();
    }
    
    
    public void safetyAlgorithm() {
        int[] work = new int[resources];
        for (int i = 0; i < resources; i++) {
            work[i] = available[i];
        }
        
        boolean[] finish = new boolean[] {false, false, false, false, false};
        
        while (count < processes) { 
            boolean flag = false; 
            for (int i = 0; i < processes; i++) { 
                if (finish[i] == false) { 
                    int j; 
                    for (j = 0; j < resources; j++) {         
                        if (need[i][j] > work[j]) {
                            break;
                        }
                    } 
                    if (j == resources) { 
                        safeSequence[count++] = i;
                        finish[i] = true;
                        flag = true;
                        for (j = 0; j < resources; j++) { 
                            work[j] = work[j] + allocation[i][j];
                        } 
                    } 
                } 
            } 
            if (flag == false) { 
                break; 
            } 
        }
        determineSafety();
    }
    
    public void determineSafety() {
        if (count < processes) { 
            System.out.println("The system is unsafe!"); 
        } else { 
            printTable();
            System.out.println("The system is safe");
            System.out.println("The safe sequence:"); 
            for (int i = 0; i < processes; i++) { 
                System.out.print("P" + safeSequence[i]); 
                if (i != processes - 1) {
                    System.out.print(" -> ");
                }
            } 
            System.out.println("");
        } 
    }
    
    public void resourceRequestAlgorithm(int processNum, int[] request) {
        int i;
        for(i = 0; i < resources; i++) {
            if(request[i] > need[processNum][i]) { 
                System.out.println("Error: Process has exceeded its maximum claim!");
                System.out.println("The system is unsafe!");
                break;
            }
        }
        
        if (i == resources) {
            int j;
            for(j = 0; j < resources; j++) {
                if (request[j] > available[j]) {
                    System.out.println("Error: Process has exceeded its maximum claim!");
                    System.out.println("The system is unsafe!");
                    break;
                }
            }
            
            if (j == resources) {
                allocateRequest(processNum, request);
                safetyAlgorithm();
            }
        }
    }
    
    public void allocateRequest(int processNum, int[] request) {
        for(int i = 0; i < resources; i++) {
            available[i] = available[i] - request[i]; 
        }
        
        for(int i = 0; i < resources; i++) {
            allocation[processNum][i] = allocation[processNum][i] + request[i];
        }
        
        for(int i = 0; i < resources; i++) {
            need[processNum][i] = need[processNum][i] - request[i];
        }
    }
}
