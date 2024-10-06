union () {
  difference () {
    cube ([14.5, 20, 9.5], center=true);
    cube ([11.5, 1000, 6.5], center=true);
  }
  union () {
    translate ([0, 0, 7.5]) {
      cube ([5, 20, 1.5], center=true);
    }
    translate ([0, 0, 5.75]) {
      cylinder ($fn=50, h=2, r=4, center=true);
    }
  }
}
