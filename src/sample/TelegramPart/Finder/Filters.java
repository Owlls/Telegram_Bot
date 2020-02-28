package sample.TelegramPart.Finder;

public class Filters {
    //Переменные для фильтра
    private double Index = -1;
    private String Location;
    private String Type;
    private String GrowType;
    private String Price;
    private boolean have_Second_Filter = false;
    //Переменные для дополнительного фильтра
    private boolean have_Fist_Filtres = false;
    private double Conformiti = -1;
    private double Service = -1;
    private double Quality = -1;
    private double Q_P = -1;
    private double Recomended = -1;
    private int NumReviews = -1;


    public Filters(){

    }


    //Методы, которые возвращают булевы массивы для клавиатур
    public boolean[] getFirstFilterK_info(){
        boolean [] keyboardMas = new boolean[6];
        if(Index != -1){
            keyboardMas[0] = true;
        }
        if(Price != null){
            keyboardMas[1] = true;
        }
        if(Location != null){
            keyboardMas[2] = true;
        }
        if(GrowType != null){
            keyboardMas[3] = true;
        }
        if(Type != null){
            keyboardMas[4] = true;
        }
        checkSecond();
        keyboardMas[5] = have_Second_Filter;
        return keyboardMas;
    }

    public boolean[] getSecondK_info(){
        boolean[] keboardMas = new boolean[6];
        if(Conformiti != -1){
            keboardMas[0] = true;
        }
        if(Service != -1){
            keboardMas[1] = true;
        }
        if(Quality != -1){
            keboardMas[2] = true;
        }
        if(Q_P != -1){
            keboardMas[3] = true;
        }
        if(Recomended != -1){
            keboardMas[4] = true;
        }
        if(NumReviews != -1){
            keboardMas[5] = true;
        }
        return keboardMas;
    }

    //Методы, которые возвращают Строковые массивы для  Текста на клавиатурах
    public String[] getFirstK_Text(){
        String[] keyboardMas = new String[6];
        if(Index != -1){
            keyboardMas[0] = String.valueOf(Index);
        }
        if(Price != null){
            keyboardMas[1] = Price;
        }
        if(Location != null){
            keyboardMas[2] = Location;
        }
        if(GrowType != null){
            keyboardMas[3] = GrowType;
        }
        if(Type != null){
            keyboardMas[4] = Type;
        }
        if(checkSecond()){
            keyboardMas[5] = "לשנות";
        }
        return keyboardMas;
    }

    public String[] getSecondK_Text(){
        String[] keboardMas = new String[6];
        if(Conformiti != -1){
            keboardMas[0] =  String.valueOf(Conformiti);
        }
        if(Service != -1){
            keboardMas[1] = String.valueOf(Service);
        }
        if(Quality != -1){
            keboardMas[2] = String.valueOf(Quality);
        }

        if(Q_P != -1){
            keboardMas[3] = String.valueOf(Q_P);
        }

        if(Recomended != -1){
            keboardMas[4] = String.valueOf(Recomended);
        }
        if(NumReviews != -1){
            keboardMas[5] = String.valueOf(NumReviews);
        }
        return keboardMas;

    }



    /***************************  Сеттеры и Геттеры для переменных фильтра*/

    public double getIndex() {
        return Index;
    }

    public void setIndex(double index) {
        Index = index;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getGrowType() {
        return GrowType;
    }

    public void setGrowType(String growType) {
        GrowType = growType;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    /***************************  Методы обнуления фильтра
     *
     */
     public void set_allnull(){
           Index = -1;
           Location = null;
           Type = null;
           GrowType = null;
           Price = null;

     }

    public void set_nullPrice(){
         Price = null;
    }

    public void set_nullIndex() {
        Index = -1;
    }

    public void set_nullLocation() {
        Location = null;
    }


    public void set_nullType() {
        Type = null;
    }

    public void set_nullGrowType() {
        GrowType = null;
    }

    public boolean choose_filters(){
         if(Index == -1 && Location == null && Type == null && GrowType == null && Price == null &&!checkSecond()){
             return false;
         } else {
             return true;
         }
    }

    /***************************  Сеттеры и Геттеры для переменных Дополнительного фильтра*/

    public double getConformiti() {
        return Conformiti;
    }

    public void setConformiti(double conformiti) {
        Conformiti = conformiti;
    }

    public double getService() {
        return Service;
    }

    public void setService(double service) {
        Service = service;
    }

    public double getQuality() {
        return Quality;
    }

    public void setQuality(double quality) {
        Quality = quality;
    }

    public double getQ_P() {
        return Q_P;
    }

    public void setQ_P(double q_P) {
        Q_P = q_P;
    }

    public double getRecomended() {
        return Recomended;
    }

    public void setRecomended(double recomended) {
        Recomended = recomended;
    }

    public int getNumReviews() {
        return NumReviews;
    }

    public void setNumReviews(int numReviews) {
        NumReviews = numReviews;
    }

    public boolean checkSecond(){
        if(Conformiti == -1 &&  Service == -1 && Quality == -1 && Q_P == -1 && Recomended == -1 && NumReviews ==  -1){
            have_Second_Filter = false;
            return false;
        } else {
            have_Second_Filter = true;
            return true;
        }
    }
    /***************************  Методы обнуления дополнительного фильтра
     *
     */
    public void set_allSnull(){
         Conformiti = -1;
         Service = -1;
         Quality = -1;
         Q_P = -1;
         Recomended = -1;
         NumReviews = -1;
         have_Second_Filter = false;
    }


    public void set_nullConformiti() {
        Conformiti = -1;
    }

    public void set_nullService() {
        Service = -1;
    }

    public void set_nullQuality() {
        Quality = -1;
    }

    public void set_nullQ_P() {
        Q_P = -1;
    }

    public void set_nullRecomended() {
        Recomended = -1;
    }

    public void set_nullNumReviews() {
        NumReviews = -1;
    }





}
