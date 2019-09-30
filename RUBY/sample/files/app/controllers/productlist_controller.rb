class ProductlistController < ApplicationController

  def index

    # @products = Productlist.all

  end

  def import
 Productlist.import(params[:file])

 redirect_to root_url, notice: "data imported"

  end

end
