package ex1.tests;
import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class RunTime {
    private static int _errors = 0, _tests = 0,_number_of_exception=0;
    private static String _log = "";

    @Test
    void time(){
        long start = new Date().getTime();
        weighted_graph Graph = new WGraph_DS();
        for (int i = 0; i < Math.pow(10,6);i++){
            Graph.addNode(i);
        }
        int i = 1;
        for (int j = 0;j<Math.pow(10,6);j++){
            Graph.connect(i,j,j/i + 1);
            i++;
            if(j == 10000){
                i = 1;
                int k =1;
                for (int n = 0; n < Math.pow(10,6);n++){
                    Graph.connect(n,k,j/i + 1);
                }
            }
        }
        new Ex1_GraphTest().graphTest_0();
        new Ex1_GraphTest().graphTest_1();
        new Ex1_GraphTest().graphTest_runtime();
        new vTest().IsConnected_DoubleCheck();
        new vTest().ModeCount();
        new vTest().save_load();
        new vTest().ShortDouble_and_Path();
        new vTest().zero_V_and_or_E();
        new vTest().SaveLoad();
        long end = new Date().getTime();
        double dt = (end-start)/1000.0;
        boolean t = dt<10;
        test("runtime test: ",t, true);

    }
    private static void test(String test, boolean val, boolean req) {
        test(test, ""+val, ""+req);
    }
    private static void test(String test, int val, int req) {
        test(test, ""+val, ""+req);
    }
    private static void test(String test, String val, String req) {
        boolean ans = true;
        ans = val.equals(req);
        String tt = _tests+") "+test+"  pass: "+ans;
        _log += "\n"+tt;
        if(!ans) {
            _errors++;
            String err = "  ERROR("+_errors+") "+val+"!="+req;
            System.err.println(tt+err);
            _log += err;

        }
        _tests++;
    }
}
