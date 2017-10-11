package engineering.software.advanced.cantoolapp.Converter.database.Impl;

/**
 * Created by lhr on 2017/10/11.
 */

public class DatabseImpl {
    @Override
    public void searchMessageUseId(Long id) {
        File filename = new File("C:\\Users\\lhr\\Desktop\\a.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String line = "";
            //读取一行
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                //判断是不是message，message类似BO_ 856 CDU_1: 8 CDU格式
                Pattern pattern = Pattern.compile("^BO_.*");
                Matcher m1 = pattern.matcher(line);
                if(m1.matches()){

                }
            }
            bufferedReader.close();// 关闭输入流
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
