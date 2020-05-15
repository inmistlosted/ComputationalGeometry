import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BezierSplain extends JFrame {
    private int margin = 50, width = 800, height = 600;

    private BezierSplain() throws HeadlessException {
        super("Сплайн Безьє");
        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(width, height);
        setLocation(350, 100);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        List<Point> points = initPoints();
        g.setColor(Color.BLUE);
        for (Point point : points){
            g.fillOval(convertX(point.getX())-2, convertY(point.getY())-2, 4, 4);
        }
        g.setColor(Color.BLACK);
        g.drawLine(convertX(0), convertY(-10), convertX(0), convertY(10));
        g.drawLine(convertX(-10), convertY(0), convertX(10), convertY(0));
        calcBSplainBezier(points, g, Color.RED);
    }

    private List<Point> initPoints(){
        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        points.add(new Point(3, 4));
        points.add(new Point(5, 3));
        points.add(new Point(7, 2));
        points.add(new Point(6, 1));
        points.add(new Point(8, 4));
        points.add(new Point(9, 1));
        points.add(new Point(10, 3));
        return points;
    }

    private int convertX(double x){
        double x_log = x / 9;
        return (int)(margin + (1.0 / 2) * (x_log + 1) * (width - 2 * margin));
    }

    private int convertY(double y){
        double y_log = y / 9;
        return (int) (margin + (-1.0 / 2) * (y_log - 1) * (height - 2 * margin));
    }

    private void calcBSplainBezier(List<Point> sourcePoints, Graphics graphics, Color color){
        List<Point> pointsToDraw = new ArrayList<>();

        for (double i = 0; i <= 1; i+=0.01) {
            pointsToDraw.add(calcBasisFunction(i, sourcePoints));
        }
        drawCurve(pointsToDraw, graphics, color);
    }

    private Point calcBasisFunction(double k, List<Point> sourcePoints) {
        double x = 0;
        double y = 0;

        int n = sourcePoints.size() - 1;
        for (int i=0; i <= n; i++)
        {
            var pow = Math.pow(1 - k, n - i);
            x += fact(n)/(fact(i)*fact(n-i)) * sourcePoints.get(i).getX() * Math.pow(k, i) * pow;
            y += fact(n)/(fact(i)*fact(n-i)) * sourcePoints.get(i).getY() * Math.pow(k, i) * pow;
        }
        return new Point(convertX(x), convertY(y));
    }

    private double fact(double arg){
        if (arg < 0) {
            throw new RuntimeException("negative argument");
        }
        if (arg == 0) {
            return 1;
        }

        double result = 1;
        for (int i=1; i<=arg; i++){
            result *= i;
        }

        return result;
    }

    private void drawCurve(List<Point> points, Graphics graphics, Color color){
        graphics.setColor(color);
        for (int i = 1; i < points.size(); i++)
        {
            int x1 = (int) points.get(i-1).getX();
            int y1 = (int) points.get(i-1).getY();
            int x2 = (int) points.get(i).getX();
            int y2 = (int) points.get(i).getY();
            graphics.drawLine(x1, y1, x2, y2);
        }
    }

    public static void main(String[] args) {
        new BezierSplain();
    }
}