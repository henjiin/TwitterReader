#!/usr/bin/gawk -f
BEGIN {
  RS="\n"
  FS=","
}
{
if($3<="2015-06-14"){
  if(numUser[$2]<100){
	numUser[$2]++
	print $0
	} 
}
}


