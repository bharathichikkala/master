class CreateProductlists < ActiveRecord::Migration[5.2]
  def change
    create_table :productlists do |t|
      t.string :orderId
      t.string :productName
      t.string :source

      t.timestamps
    end
  end
end
