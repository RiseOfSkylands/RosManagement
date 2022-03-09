package ROS;

import Enums.CountType;
import Enums.Sign;

import java.lang.reflect.Field;

public class CountData {
    public Long corn;
    public Long wood;
    public Long stone;
    public Long gold;
    public Long gems;
    public Long power;
    public Long vip;

    public CountData(Long corn, Long wood, Long stone, Long gold, Long gems, Long power, Long vip){

        this.corn = corn;
        this.wood = wood;
        this.stone = stone;
        this.gold = gold;
        this.gems = gems;
        this.power = power;
        this.vip = vip;
    }

    public CountData(){
        corn = 0L;
        wood = 0L;
        stone = 0L;
        gold = 0L;
        gems = 0L;
        power = 0L;
        vip = 0L;
    }

    public void edit(CountType countType, Sign sign, Long amount){
        switch(countType){
            case CORN:
                System.out.println(amount);
                if(sign.equals(Sign.ADD)){
                    this.corn += amount;
                }else{
                    this.corn -= amount;
                }
                break;
            case WOOD:
                if(sign.equals(Sign.ADD)){
                    this.wood += amount;
                }else{
                    this.wood -= amount;
                }
                break;
            case STONE:
                if(sign.equals(Sign.ADD)){
                    this.stone += amount;
                }else{
                    this.stone -= amount;
                }
                break;
            case GOLD:
                if(sign.equals(Sign.ADD)){
                    this.gold += amount;
                }else{
                    this.gold -= amount;
                }
                break;
            case GEMS:
                if(sign.equals(Sign.ADD)){
                    this.gems += amount;
                }else{
                    this.gems -= amount;
                }
                break;
            case POWER:
                if(sign.equals(Sign.ADD)){
                    this.power += amount;
                }else{
                    this.power -= amount;
                }
                break;
            case VIP:
                if(sign.equals(Sign.ADD)){
                    this.vip += amount;
                }else{
                    this.vip -= amount;
                }
                break;
        }
    }

}
