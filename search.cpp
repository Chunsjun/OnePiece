#include <cstdio>
#include <fcntl.h>
#include <unistd.h>

#include <algorithm>
#include <string>

#include "filefinder.h"
#include "options.h"
#include "query_string.h"
#include "terminal.h"

int main(int argc, char* argv[]){
  if(!get_options(argc, argv)){
    return 1;
  }
  if(options.show_help){
    print_help();
    return 0;
  }
  if(options.show_version){
    print_version();
    return 0;
  }

  find_files();

  std::sort(files.begin(),files.end(),[](const node& n1, const node& n2){
            return n1.level < n2.level;
            });

  query_string qstr;

  terminal term;
  int c;

  unsigned selected = 0;
  std::vector<node> results;
  term.print_search_line(qstr.get_str(),qstr.get_pos());
  while(1){
    c = getchar();
    if(c == 27){ // esc
      c=getchar();
      if(c == '['){
        c=getchar();
        if(c == 'A'){ // up
          if(selected > 0){
            selected--;
          }
          term.print_result(results, selected);
          term.restore_cursor_pos();
          term.cursor_right(qstr.get_pos()+1);
        }else if(c == 'B'){ // down
          selected++;
          if(selected > results.size()-1){
            selected = results.size()-1;
          }
          term.print_result(results, selected);
          term.restore_cursor_pos();
          term.cursor_right(qstr.get_pos()+1);
        }else if(c == 'C'){ // right
          if(qstr.cursor_right()){
            term.cursor_right();
          }
        }else if(c == 'D'){ // left
          if(qstr.cursor_left()){
            term.cursor_left();
          }
        }else if(c == 'Z'){ // shift-tab
          if(selected > 0){
            selected--;
          }else{
            selected = results.size()-1;
          }
          term.print_result(results, selected);
          term.restore_cursor_pos();
          term.cursor_right(qstr.get_pos()+1);
        }
      }
    }else if(c == 9){ // tab
      selected++;
      if(selected > results.size()-1){
        selected = 0;
      }
      term.print_result(results, selected);
      term.restore_cursor_pos();
      term.cursor_right(qstr.get_pos()+1);
    }else if(c == 10){ // enter
      if(results.size()!=0){
        if(results[selected].is_dir){
          fprintf(stdout,"%s%s\n",results[selected].location.c_str(),results[selected].filename.c_str());
        }else{
          fprintf(stdout,"%s\n",results[selected].location.c_str());
        }
      }
      fprintf(stderr,"\n");
      return 0;
    }else if(c == 127){ //backspace
      qstr.remove();
      term.print_search_line(qstr.get_str(),qstr.get_pos());
      results = search(qstr);
      if(selected >= results.size()){
        selected = results.size()-1;
      }
      if(results.size()==0){
        selected = 0;
      }
      term.print_result(results, selected);
      term.restore_cursor_pos();
      term.cursor_right(qstr.get_pos()+1);
    }else{
      qstr.add(c);
      term.print_search_line(qstr.get_str(),qstr.get_pos());
      results = search(qstr);
      if(selected >= results.size()){
        selected = results.size()-1;
      }
      if(results.size()==0){
        selected = 0;
      }
      term.print_result(results, selected);
      term.restore_cursor_pos();
      term.cursor_right(qstr.get_pos()+1);
    }
  }
}
