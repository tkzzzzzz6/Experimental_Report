// Rectangle class definition and GetRect function
class Rectangle
{
    /********* Begin *********/
    // 返回一个 h*w 的 Rectangle 对象
    private:
        int height;
        int width;
    public:
        Rectangle(int h, int w);
        int getArea();
    /********* End *********/
};

Rectangle::Rectangle(int h, int w)
{
    height = h;
    width = w;
}

int Rectangle::getArea()
{
    return height * width;
}

Rectangle GetRect(int h, int w)
{
    return Rectangle(h, w);
}

int GetRectArea(Rectangle rect)
{
    /********* Begin *********/
    // 返回 rect 对象的面积
    return rect.getArea();
    /********* End *********/
}
