class ProductController < ApplicationController

  def index


    require 'roo'

    xlsx = Roo::Excelx.new(File.expand_path('E:/RUBY/checklistsample.xlsx'))

    xlsx.each_row_streaming(offset: 1) do |row|
      Product.find_or_create_by(orderId: row[0].value, productName: row[1].value,source: row[2].value)
    end
  end




def CSV

require 'csv'

path = Rails.root.join('lib', 'seeds', "E:/RUBY/checklistcsv.csv")

CSV.foreach(path, :headers => true, encoding: "UTF-8") do |row|
   Product.create! row.to_hash

   put Product.source
end

end


end
