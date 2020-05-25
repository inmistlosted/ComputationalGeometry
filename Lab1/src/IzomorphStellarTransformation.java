import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class IzomorphStellarTransformation extends JFrame
{
    private int margin = 50, width = 800, height = 600, radius = 30, flag = 0;
    private int n = 9, xZ1, xZ2, yZ1, yZ2, angleZ1, angleZ2;
    boolean rep = false;

    private ArrayList<String> results;
    private ArrayList<Point.Double> Z1, Z2;
    private ArrayList<Point.Double> Z1draw, Z2draw;

    public IzomorphStellarTransformation(int n, int xZ1, int yZ1, int angleZ1)
    {

        super("Ізоморфне перетворення зірковий многокутників");
        this.n = n;
        this.xZ1 = xZ1;
        this.xZ2 = xZ2;
        this.yZ1 = yZ1;
        this.yZ2 = yZ2;
        this.angleZ1 = angleZ1;
        this.angleZ2 = angleZ2;

        Z1 = new ArrayList<>();
        Z2 = new ArrayList<>();
        initStellatingPolygon(xZ1, yZ1, radius, angleZ1, Z1);
        initStellatingPolygon(xZ2, yZ2, radius, angleZ2, Z2);
        //findTransformation();

        setLayout(new GridLayout(30, 2));
        for (int i = 0; i < 21; i++) {
            add(Box.createHorizontalBox());
        }
//        for (int i = 0; i < results.size(); i++) {
//            JLabel label = new JLabel(results.get(i));
//            add(label);
//        }

        JFrame.setDefaultLookAndFeelDecorated(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(width, height + 200);
        setLocation(350, 0);
        setResizable(false);
        setVisible(true);

        new Thread(() -> {
           while (true){
//               try {
//                   sleep(1);
//               } catch (InterruptedException e) {
//                   e.printStackTrace();
//               }
               repaint();
           }
        }).start();
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawLine(convertX(0), convertY(-100), convertX(0), convertY(100));
        g.drawLine(convertX(-100), convertY(0), convertX(100), convertY(0));
        if (!rep){
            Z1draw = drawStellatingPolygon(xZ1, yZ1, radius, angleZ1, g, Color.BLUE, 1);
            Z2draw = drawStellatingPolygon(xZ1, yZ1, radius*2, angleZ1+30,  g, Color.RED, 2);
            rep = true;
        } else {
            Z1draw = drawProgress(Z1draw, Z2draw, Color.BLUE, g);
            Z2draw = drawStellatingPolygon(xZ1, yZ1, radius*2, angleZ1+30,  g, Color.RED, 2);
        }

        findLinearInterpolation(g, Color.BLACK);
        if (flag == 0){
            repaint();
            flag++;
        }

    }



    private int convertX(double x){
        double x_log = x / 100;
        return (int)(margin + (1.0 / 2) * (x_log + 1) * (width - 2 * margin));
    }

    private int convertY(double y){
        double y_log = y / 100;
        return (int) (margin + (-1.0 / 2) * (y_log - 1) * (height - 2 * margin));
    }

    private void initStellatingPolygon(int x0, int y0, int radius, int turn, ArrayList<Point.Double> polygon){
        double angle = 2*Math.PI / n;
        double x1 = x0 - radius;
        double rotation = turn * (Math.PI / 180), y1 = y0, x2, y2;

        polygon.add(new Point.Double(x1, y1));
        for (int i = 0; i < n; i++) {
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            x2 = (x1 - x0) * cos - (y1 - y0) * sin + x0;
            y2 = (x1 - x0) * sin + (y1 - y0) * cos + y0;
            x1 = x2;
            y1 = y2;
            polygon.add(new Point.Double(x1, y1));
        }

        for (Point.Double aDouble : polygon) {
            double newX = (aDouble.x - x0) * Math.cos(rotation) - (aDouble.y - y0) * Math.sin(rotation) + x0;
            double newY = (aDouble.x - x0) * Math.sin(rotation) + (aDouble.y - y0) * Math.cos(rotation) + y0;
            aDouble.setLocation(newX, newY);
        }
    }

    private ArrayList<Point.Double> drawStellatingPolygon(int x0, int y0, int radius, int turn,  Graphics graphics, Color color, int num){
        graphics.setColor(color);

        ArrayList<Point> polToDraw = new ArrayList<>();
        ArrayList<Point.Double> polygon = new ArrayList<>();
        double angle = 2*Math.PI / n, x1 = x0 - radius, y1 = y0, x2, y2;
        int retreat = (n - 5) / 2 + 1;
        double rotation = turn * (Math.PI / 180);

        polygon.add(new Point.Double(x1, y1));
        for (int i = 0; i < n; i++) {
            x2 = (x1 - x0) * Math.cos(angle) - (y1 - y0) * Math.sin(angle) + x0;
            y2 = (x1 - x0) * Math.sin(angle) + (y1 - y0) * Math.cos(angle) + y0;
            x1 = x2;
            y1 = y2;
            polygon.add(new Point.Double(x1, y1));
        }

        for (int i = 0; i < polygon.size(); i++) {
            double cos = Math.cos(rotation);
            double sin = Math.sin(rotation);
            double newX = (polygon.get(i).x - x0) * cos - (polygon.get(i).y - y0) * sin + x0;
            double newY = (polygon.get(i).x - x0) * sin + (polygon.get(i).y - y0) * cos + y0;
            polygon.get(i).setLocation(newX, newY);
            polToDraw.add(new Point(convertX(newX), convertY(newY)));
        }

        for (int i = 0; i < n-2; i++) {
            for (int j = 0; j < polygon.size(); j++) {
                if (n % 2 == 1){
                    if (j == i + retreat + 1 || j == i + retreat + 2){
                        graphics.drawLine(polToDraw.get(i).x, polToDraw.get(i).y, polToDraw.get(j).x, polToDraw.get(j).y);
                    }
                } else {
                    if (j == i + retreat + 1 || j == i + retreat + 3){
                        graphics.drawLine(polToDraw.get(i).x, polToDraw.get(i).y, polToDraw.get(j).x, polToDraw.get(j).y);
                    }
                }
            }
        }
        return polygon;
    }

    private double line(Point.Double p1, Point.Double p2, double x){
        return ((x - p1.x) * (p2.y - p1.y)) / (p2.x - p1.x) + p1.y;
    }

    private ArrayList<Point.Double> drawProgress(ArrayList<Point.Double> pol1, ArrayList<Point.Double> pol2, Color color, Graphics graphics){
        graphics.setColor(color);

        for (int i = 0; i < pol1.size(); i++) {
            Point.Double p1 = pol1.get(i);
            Point.Double p2 = pol2.get(i);

            if (p1.x > p2.x){
                pol1.get(i).setLocation(pol1.get(i).x - 0.1, line(p1, p2, pol1.get(i).x - 0.1));
            } else {
                pol1.get(i).setLocation(pol1.get(i).x + 0.1, line(p1, p2, pol1.get(i).x + 0.1));
            }


        }

        ArrayList<Point> polToDraw = new ArrayList<>();
        int retreat = (n - 5) / 2 + 1;

        for (Point.Double aDouble : pol1) {
            polToDraw.add(new Point(convertX(aDouble.x), convertY(aDouble.y)));
        }

        for (int i = 0; i < n-2; i++) {
            for (int j = 0; j < pol1.size(); j++) {
                if (n % 2 == 1){
                    if (j == i + retreat + 1 || j == i + retreat + 2){
                        graphics.drawLine(polToDraw.get(i).x, polToDraw.get(i).y, polToDraw.get(j).x, polToDraw.get(j).y);
                    }
                } else {
                    if (j == i + retreat + 1 || j == i + retreat + 3){
                        graphics.drawLine(polToDraw.get(i).x, polToDraw.get(i).y, polToDraw.get(j).x, polToDraw.get(j).y);
                    }
                }
            }
        }
        return pol1;
    }

    private void findTransformation(){
        double[] x = new double[2];
        double[] y = new double[2];
        double[] trans = new double[2];

        double[][] matrix1 = new double[4][4];
        double[][] matrix2 = new double[4][4];
        double[][] divider = new double[3][3];

        for (int i = 0; i < divider.length; i++) {
            for (int j = 0; j < divider[i].length; j++) {
                switch (i){
                    case 0 :
                        divider[i][j] = Z1.get(j).x;
                        break;
                    case 1 :
                        divider[i][j] = Z1.get(j).y;
                        break;
                    case 2 :
                        divider[i][j] = 1;
                        break;
                }
            }
        }

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 1; j < matrix1[i].length; j++) {
                switch (i){
                    case 0 :
                        matrix1[i][j] = Z2.get(j).x;
                        matrix2[i][j] = Z2.get(j).y;
                        break;
                    case 1 :
                        matrix1[i][j] = Z1.get(j).x;
                        matrix2[i][j] = Z1.get(j).x;
                        break;
                    case 2 :
                        matrix1[i][j] = Z1.get(j).y;
                        matrix2[i][j] = Z1.get(j).y;
                        break;
                    case 3 :
                        matrix1[i][j] = 1;
                        matrix2[i][j] = 1;
                        break;
                }
            }
        }

        double dividerDet = divider[0][0] * divider[1][1] * divider[2][2]
                + divider[0][2] * divider[1][0] * divider[2][1]
                + divider[0][1] * divider[1][2] * divider[2][0]
                - divider[0][2] * divider[1][1] * divider[2][0]
                - divider[0][0] * divider[1][2] * divider[2][1]
                - divider[0][1] * divider[1][0] * divider[2][2];

        double sum = matrix1[0][1] * matrix1[2][2] * matrix1[3][3]
                + matrix1[0][3] * matrix1[2][1] * matrix1[3][2]
                + matrix1[0][2] * matrix1[2][3] * matrix1[3][1];

        double differ = matrix1[0][3] * matrix1[2][2] * matrix1[3][1]
                + matrix1[0][1] * matrix1[2][3] * matrix1[3][2]
                + matrix1[0][2] * matrix1[2][1] * matrix1[3][3];
        x[0] = (sum - differ) / dividerDet;

        sum = matrix2[0][1] * matrix2[2][2] * matrix2[3][3]
                + matrix2[0][3] * matrix2[2][1] * matrix2[3][2]
                + matrix2[0][2] * matrix2[2][3] * matrix2[3][1];
        differ = matrix2[0][3] * matrix2[2][2] * matrix2[3][1]
                + matrix2[0][1] * matrix2[2][3] * matrix2[3][2]
                + matrix2[0][2] * matrix2[2][1] * matrix2[3][3];
        x[1] =  (sum - differ) / dividerDet;

        sum = matrix1[0][1] * matrix1[1][2] * matrix1[3][3]
                + matrix1[0][3] * matrix1[1][1] * matrix1[3][2]
                + matrix1[0][2] * matrix1[1][3] * matrix1[3][1];
        differ = matrix1[0][3] * matrix1[1][2] * matrix1[3][1]
                + matrix1[0][1] * matrix1[1][3] * matrix1[3][2]
                + matrix1[0][2] * matrix1[1][1] * matrix1[3][3];
        y[0] =  (sum - differ) / dividerDet;

        sum = matrix2[0][1] * matrix2[1][2] * matrix2[3][3]
                + matrix2[0][3] * matrix2[1][1] * matrix2[3][2]
                + matrix2[0][2] * matrix2[1][3] * matrix2[3][1];
        differ = matrix2[0][3] * matrix2[1][2] * matrix2[3][1]
                + matrix2[0][1] * matrix2[1][3] * matrix2[3][2]
                + matrix2[0][2] * matrix2[1][1] * matrix2[3][3];
        y[1] =  (sum - differ) / dividerDet;

        sum = matrix1[0][1] * matrix1[1][2] * matrix1[2][3]
                + matrix1[0][3] * matrix1[1][1] * matrix1[2][2]
                + matrix1[0][2] * matrix1[1][3] * matrix1[2][1];
        differ = matrix1[0][3] * matrix1[1][2] * matrix1[2][1]
                + matrix1[0][1] * matrix1[1][3] * matrix1[2][2]
                + matrix1[0][2] * matrix1[1][1] * matrix1[2][3];
        trans[0] =  (sum - differ) / dividerDet;

        sum = matrix2[0][1] * matrix2[1][2] * matrix2[2][3]
                + matrix2[0][3] * matrix2[1][1] * matrix2[2][2]
                + matrix2[0][2] * matrix2[1][3] * matrix2[2][1];
        differ = matrix2[0][3] * matrix2[1][2] * matrix2[2][1]
                + matrix2[0][1] * matrix2[1][3] * matrix2[2][2]
                + matrix2[0][2] * matrix2[1][1] * matrix2[2][3];
        trans[1] =  (sum - differ) / dividerDet;


        results  = new ArrayList<>();
        results.add("<html><p style='padding-left: 40px; font-size: 15px'>Ізоморфне перетворення зіркових " + n
                + "-кутників <font color='blue'>Z1</font> в <font color='red'>Z2</font> має вигляд: </p></html>");
        results.add("");
        results.add("<html><p style='padding-left: 180px; font-size: 12px'>( " + Math.round(y[0] * 100) / 100.0 + "   "
                + Math.round(x[0] * 100) / 100.0 + " )( x ) + ( " + Math.round(trans[0] * 100) / 100.0 + " )</p></html>");
        results.add("<html><p style='padding-left: 180px; font-size: 12px'>( " + Math.round(y[1] * 100) / 100.0 + "   "
                + Math.round(x[1] * 100) / 100.0 + " )( y )  ( " + Math.round(trans[1] * 100) / 100.0 + " )</p></html>");
        results.add("");
        results.add("<html><p style='padding-left: 120px; font-size: 12px'>Кут повороту: " + Math.round(Math.acos(x[0]) * 180 / Math.PI) + "&deg</p></html>");
        results.add("");
        results.add("<html><p style='padding-left: 120px; font-size: 12px'>Трансляція на: ( " + Math.round(trans[0] * 100) / 100.0
                + ", " + Math.round(trans[1] * 100) / 100.0 + " )</p></html>");
    }

    private void findLinearInterpolation(Graphics g, Color color){
        g.setColor(color);
        for (int i = 0; i < Z1draw.size(); i++) {
            g.drawLine(convertX(Z1draw.get(i).x), convertY(Z1draw.get(i).y), convertX(Z2draw.get(i).x), convertY(Z2draw.get(i).y));
        }
    }
}