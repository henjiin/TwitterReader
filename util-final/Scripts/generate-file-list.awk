#!/usr/bin/gawk -f
BEGIN {
  RS="\n"
  FS=" "
  x=0
  regex="[a-zA-Z0-9]+.json"
}
{
  if(match($9,regex)){
  print $9
  }
}
END {
  print x
}
