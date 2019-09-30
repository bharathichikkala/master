class FileController < ApplicationController

def index
# File.open("fruits.txt", "r+") do |file|
#    @file = file.readlines

@file =File.read("E:/RUBY/fruits.txt")
end



def show
  File.open("E:/RUBY/writefile.xls","w") do |file|
     file.write "I am the test text!!!"
     file.write "...hear me roar."
 end
end


def readexcel

  require 'rubyXL'

workbook = RubyXL::Parser.parse 'E:/RUBY/checklistsample.xlsx'
worksheets = workbook.worksheets
puts "Found #{worksheets.count} worksheets"

worksheets.each do |worksheet|

  puts "Reading: #{worksheet.sheet_name}"
  num_rows = 0
 @records = Array.new
  worksheet.each do |row|
    row_cells = row.cells.map{ |cell| cell.value }
    num_rows += 1
    @records.push(row_cells)

    puts @records

    # uncomment to print out row values
    # puts row_cells.join " "
  end

  puts "Read #{num_rows} rows"

end

end


def readpdf

    reader = PDF::Reader.new('E:/RUBY/spring-framework-reference.pdf')
    reader.pages.each do |page|
      @pages=page.text
            puts page.text
          end
    puts reader.pdf_version
    puts reader.info
    puts reader.metadata
    puts reader.page_count
end

def csv

  require 'csv'

  @csv =CSV.read('E:/RUBY/checklistcsv.csv')

    puts@csv

end

end
