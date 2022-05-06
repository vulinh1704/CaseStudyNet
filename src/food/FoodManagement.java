package food;

import readandwritefile.ReadAndWriteAccountFile;

import java.util.ArrayList;
import java.util.List;

public class FoodManagement {
    List<Foot> footList = new ArrayList<>();

    public void addFoot(Foot foot) {
        footList.add(foot);
        ReadAndWriteAccountFile.writeToFileFoot(footList);
    }

    public void deleteFoot(String product) {
        footList.remove(searchFoot(product));
    }

    public void editFoot(String product, Foot foot) {
        footList.set(searchFoot(product), foot);
    }

    public int searchFoot(String product) {
        for (int i = 0; i < footList.size(); i++) {
            if (product.equals(footList.get(i).getProduct())) {
                return i;
            }
        }
        return -1;
    }
}
