$(function () {
    "use strict";
    const url = window.location + "";
    const path = url.replace(
      window.location.protocol + "//" + window.location.host + "/",
      ""
    );
    const element = $("ul#sidebarnav a").filter(function () {
      return this.href === url || this.href === path; // || url.href.indexOf(this.href) === 0;
    });
    element.parentsUntil(".sidebar-nav").each(function (index) {
        const currentElement = $(this);
      if (currentElement.is("li") && currentElement.children("a").length !== 0) {
          currentElement.children("a").addClass("active");
          currentElement.parent("ul#sidebarnav").length === 0
          ? currentElement.addClass("active")
          : currentElement.addClass("selected");
      } else if (!currentElement.is("ul") && currentElement.children("a").length === 0) {
          currentElement.addClass("selected");
      } else if (currentElement.is("ul")) {
          currentElement.addClass("in");
      }
    });
  
    element.addClass("active");
    $("#sidebarnav a").on("click", function (e) {
      if (!$(this).hasClass("active")) {
        // hide any open menus and remove all other classes
        $("ul", $(this).parents("ul:first")).removeClass("in");
        $("a", $(this).parents("ul:first")).removeClass("active");
  
        // open our new menu and add the open class
        $(this).next("ul").addClass("in");
        $(this).addClass("active");
      } else if ($(this).hasClass("active")) {
        $(this).removeClass("active");
        $(this).parents("ul:first").removeClass("active");
        $(this).next("ul").removeClass("in");
      }
    });
    $("#sidebarnav >li >a.has-arrow").on("click", function (e) {
      e.preventDefault();
    });
  });