package com.example.qingong2.homepage;

public class Apply {

    private String name;//apply名称
    private int imageId;//图片资源id

    public Apply(String name,int imageId)
    {
        this.imageId=imageId;
        this.name=name;
    }

    public String getName()/*获取名称*/
    {
        return name;
}

    public int getImageId()/*获取id*/
    {
        return imageId;
    }
}
