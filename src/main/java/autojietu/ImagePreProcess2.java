package autojietu;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

public class ImagePreProcess2 {

    private static Map<BufferedImage, String> trainMap = null;
    private static int index = 0;

    public static int isBlack(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
            return 1;
        }
        return 0;
    }

    public static int isWhite(int colorInt) {
        Color color = new Color(colorInt);
        if (color.getRed() + color.getGreen() + color.getBlue() > 100) {
            return 1;
        }
        return 0;
    }

    public static BufferedImage removeBackgroud(String picFile)
            throws Exception {
        BufferedImage img = ImageIO.read(new File(picFile));
        return img;
    }

    public static BufferedImage removeBlank(BufferedImage img) throws Exception {
        int width = img.getWidth();
        int height = img.getHeight();
        int start = 0;
        int end = 0;
        Label1: for (int y = 0; y < height; ++y) {
            int count = 0;
            for (int x = 0; x < width; ++x) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    count++;
                }
                if (count >= 1) {
                    start = y;
                    break Label1;
                }
            }
        }
        Label2: for (int y = height - 1; y >= 0; --y) {
            int count = 0;
            for (int x = 0; x < width; ++x) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    count++;
                }
                if (count >= 1) {
                    end = y;
                    break Label2;
                }
            }
        }
        return img.getSubimage(0, start, width, end - start + 1);
    }

    public static List<BufferedImage> splitImage(BufferedImage img)
            throws Exception {
        List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
        int width = img.getWidth();
        int height = img.getHeight();
        List<Integer> weightlist = new ArrayList<Integer>();
        for (int x = 0; x < width; ++x) {
            int count = 0;
            for (int y = 0; y < height; ++y) {
                if (isWhite(img.getRGB(x, y)) == 1) {
                    count++;
                }
            }
            weightlist.add(count);
        }
        for (int i = 0; i < weightlist.size();) {
            int length = 0;
            while (weightlist.get(i++) > 1) {
                length++;
            }
            if (length > 12) {
                subImgs.add(removeBlank(img.getSubimage(i - length - 1, 0,
                        length / 2, height)));
                subImgs.add(removeBlank(img.getSubimage(i - length / 2 - 1, 0,
                        length / 2, height)));
            } else if (length > 3) {
                subImgs.add(removeBlank(img.getSubimage(i - length - 1, 0,
                        length, height)));
            }
        }
        return subImgs;
    }

    public static Map<BufferedImage, String> loadTrainData() throws Exception {
        if (trainMap == null) {
            Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
            File dir = new File("D:\\pic\\train2");
            File[] files = dir.listFiles();
            for (File file : files) {
                map.put(ImageIO.read(file), file.getName().charAt(0) + "");
            }
            trainMap = map;
        }
        return trainMap;
    }

    public static String getSingleCharOcr(BufferedImage img,
                                          Map<BufferedImage, String> map) {
        String result = "";
        int width = img.getWidth();
        int height = img.getHeight();
        int min = width * height;
        for (BufferedImage bi : map.keySet()) {
            int count = 0;
            int widthmin = width < bi.getWidth() ? width : bi.getWidth();
            int heightmin = height < bi.getHeight() ? height : bi.getHeight();
            Label1: for (int x = 0; x < widthmin; ++x) {
                for (int y = 0; y < heightmin; ++y) {
                    if (isWhite(img.getRGB(x, y)) != isWhite(bi.getRGB(x, y))) {
                        count++;
                        if (count >= min)
                            break Label1;
                    }
                }
            }
            if (count < min) {
                min = count;
                result = map.get(bi);
            }
        }
        return result;
    }

    public static String getAllOcr(String file) throws Exception {
        BufferedImage img = removeBackgroud(file);
        List<BufferedImage> listImg = splitImage(img);
        Map<BufferedImage, String> map = loadTrainData();
        String result = "";
        for (BufferedImage bi : listImg) {
            result += getSingleCharOcr(bi, map);
        }
        ImageIO.write(img, "JPG", new File("D:\\pic\\result3\\" + result + ".jpg"));
        return result;
    }


    public static void trainData() throws Exception {
        File dir = new File("D:\\pic\\img3");
        File[] files = dir.listFiles();
        for (File file : files) {
            BufferedImage img = removeBackgroud("D:\\pic\\img3\\" + file.getName());
            List<BufferedImage> listImg = splitImage(img);
            if (listImg.size() == 4) {
                for (int j = 0; j < listImg.size(); ++j) {
                    ImageIO.write(listImg.get(j), "JPG", new File("D:\\pic\\train3\\"
                            + file.getName().charAt(j) + "-" + (index++)
                            + ".jpg"));
                }
            }
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        trainData();
        // downloadImage();
            String text = getAllOcr("D:\\pic\\img3\\0.jpg");
            System.out.println("0.jpg = " + text);
    }
}
