package model;

public class Reward {
    private int id;
    private String rewardName;

    public Reward(int id, String rewardName) {
        this.id = id;
        this.rewardName = rewardName;
    }

    public int getId() { 
      return id; }
  
    public String getRewardName() { 
      return rewardName; }

    @Override
    public String toString() {
        return "Reward{id=" + id + ", name='" + rewardName + "'}";
    }
}
